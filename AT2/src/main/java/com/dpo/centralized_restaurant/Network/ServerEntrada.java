package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;
import com.dpo.centralized_restaurant.database.ConectorDB;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles the connection between the system, server side, with the general server and the database
 */
public class ServerEntrada extends Thread {

    private int PORT;
    private ServerSocket serverSocket;
    private final ArrayList<DedicatedServerEntrada> dedicatedServers;
    private DedicatedServerEntrada dedicatedServer;
    private RequestManager requestsManager;
    private ConectorDB conectorDB;
    private Controller controller;
    private boolean isRunning;

    public ServerEntrada(configJson config, ConectorDB conectorDB, Controller controller) {
        dedicatedServers = new ArrayList<>(1);
        serverSocket = null;
        requestsManager = new RequestManager();
        PORT = config.getPort_Entrada();
        this.conectorDB = conectorDB;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;

            while (isRunning) {
                System.out.println("ruuuuuuun boiii 2");
                if(dedicatedServers.isEmpty()){
                    System.out.println(isRunning + " is running1");
                    Socket socket = serverSocket.accept();  // Esperem a que algun usuari es connecti
                    System.out.println(isRunning + " is running2");
                    System.out.println("Connected");
                    //Modified by: Marc --> Added dedicatedServers in constructor
                    DedicatedServerEntrada dServer = new DedicatedServerEntrada(socket, requestsManager, dedicatedServers, conectorDB, controller);   // Creem un cami dedicat a la connexio amb aquest usuari
                    System.out.println(isRunning + " is running3");
                    dedicatedServers.add(dServer);
                    dedicatedServer = dServer;
                    System.out.println(isRunning + " is running4");
                    if  (dedicatedServers.isEmpty()){
                        System.out.println("empty - running");
                    }else{
                        System.out.println("no empty - running");
                    }
                    System.out.println(isRunning + " is running5");
                    dServer.start();
                    System.out.println("uala chuta");
                    System.out.println(isRunning + " is running");
                }
            }

        } catch (IOException e) {
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    System.out.println("clouseo");
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Finishes the connection between the system from the Server side
     */
    public void closeServer(){
        isRunning = false;
        //this.stop();
        try {
            serverSocket.close();
            System.out.println("close");
        } catch (IOException e) {

        }
        System.out.println("closeServer()1");
        dedicatedServers.get(0).closeDedicatedServer();
        System.out.println("closeServer()2");
        dedicatedServer.closeDedicatedServer();
        System.out.println("clouseo server");
        dedicatedServer = null;
        dedicatedServers.remove(0);
        try {
            System.out.println("clouseo 2");
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a new request
     * @param nuevoRequest
     */
    public void updateAssignment(Request nuevoRequest){
        try {
            if  (dedicatedServers.isEmpty()){
                System.out.println("empty");
            }
            dedicatedServer.sendPass(nuevoRequest);
        }catch (NullPointerException e){
            System.out.println("null pointer");
        }catch (IndexOutOfBoundsException e1){
            System.out.println("index out of");
        }
    }

    /**
     * Sends all the request within the list
     * @param listaRequests
     */
    public void updateAll(ArrayList<Request> listaRequests){
        try {
            dedicatedServers.get(0).sendAll(listaRequests);
        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException e1){

        }
    }

}
