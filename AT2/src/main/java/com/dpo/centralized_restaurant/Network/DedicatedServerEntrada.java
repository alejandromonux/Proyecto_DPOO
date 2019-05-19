package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;
import com.dpo.centralized_restaurant.database.ConectorDB;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles the connection between the system, server side, using sockets
 */
public class DedicatedServerEntrada extends Thread{

    private RequestManager requestManager;
    private ArrayList<DedicatedServerEntrada> dedicatedServers;
    private ConectorDB conectorDB;
    private Controller controller;


    private final Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean start;


    public DedicatedServerEntrada(Socket socket, RequestManager requestsManager, ArrayList<DedicatedServerEntrada> dedicatedServers, ConectorDB conectorDB, Controller controller) {
        this.socket = socket;
        this.requestManager = requestsManager;

        //Add by: Marc --> arraylist of dedicatedServers to delete himself when connection close
        this.dedicatedServers = dedicatedServers;
        this.conectorDB = conectorDB;
        this.controller = controller;
        start = true;

    }

    @Override
    public void run() {
        try {
            String init;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            while (start) {
                init = dis.readUTF();
                switch (init) {
                    case "REQUEST-COMING":
                        dos.writeUTF("REQUEST-COMING");
                        String nameNew = dis.readUTF();
                        int cantidadPersonas = dis.readInt();
                        boolean p = conectorDB.insertRequest(nameNew, cantidadPersonas);
                        dos.writeBoolean(p);
                        controller.actualizarVistaRequests(conectorDB.getRequestsPendientes());
                        break;

                    case "NEED-REQUEST-LIST":
                        sendAll(conectorDB.getRequests());
                        break;

                    case "DELETE-REQUEST":
                        String id = dis.readUTF();
                        boolean done = conectorDB.deleteRequest(id);
                        dos.writeUTF("DELETE-RESPONSE");
                        dos.writeBoolean(done);

                        if(done){
                            sendAll(conectorDB.getRequests());
                            controller.actualizarVistaRequests(conectorDB.getRequestsPendientes());
                        }
                        break;

                }

            }

        } catch (IOException e) {

        } finally {
            try {
                dos.close();
            } catch (IOException e) {}
            try {
                dis.close();
            } catch (IOException e) {}
            //try {
                dedicatedServers.remove(this);
                //socket.close();  --Marc: No se si esto deberia estar ya que se cierra a el mismo
            //} catch (IOException e) {}
        }
    }

    /**
     * Finish the connection between the server and the system
     */
    public void closeDedicatedServer(){
        start = false;
        try {
            dos.close();
            dis.close();
        } catch (IOException e) {}
        //try {
        dedicatedServers.remove(this);
    }

    /**
     * Sends the assigned request trough the connection between server and client
     * @param request
     */
    public void sendPass(Request request){

        synchronized (this) {
            try {
                dos.writeUTF("INCOMING-ASSIGNMENT");
                dos.writeInt(request.getId());
                dos.writeUTF(request.getName());
                dos.writeUTF(request.getPassword());
                dos.writeUTF(request.getMesa_name());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Sends all the requests trough the connection between server and client
     * @param listaRequests
     */
    public void sendAll(ArrayList<Request> listaRequests){
        synchronized (this) {
            try {
                dos.writeUTF("UPDATE-REQUEST-LIST");
                dos.writeInt(listaRequests.size());
                for (Request request : listaRequests){
                    dos.writeInt(request.getId());
                    if (request.getName() != null) {
                        dos.writeUTF(request.getName());
                    }else{
                        dos.writeUTF("NULL");
                    }
                    if (request.getPassword() != null) {
                        dos.writeUTF(request.getPassword());
                    }else{
                        dos.writeUTF("NULL");
                    }
                    if (request.getMesa_name() != null){
                        dos.writeUTF(request.getMesa_name());
                    }else{
                        dos.writeUTF("NULL");
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
