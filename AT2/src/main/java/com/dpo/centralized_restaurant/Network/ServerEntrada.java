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

                if(dedicatedServers.isEmpty()){
                    Socket socket = serverSocket.accept();  // Esperem a que algun usuari es connecti
                    System.out.println("Connected");
                    //Modified by: Marc --> Added dedicatedServers in constructor
                    DedicatedServerEntrada dServer = new DedicatedServerEntrada(socket, requestsManager, dedicatedServers, conectorDB, controller);   // Creem un cami dedicat a la connexio amb aquest usuari
                    dedicatedServers.add(dServer);
                    dedicatedServer = dServer;
                    dServer.start();
                }
            }

        } catch (IOException e) {
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
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
        } catch (IOException e) {

        }
        dedicatedServers.get(0).closeDedicatedServer();
        dedicatedServer.closeDedicatedServer();
        dedicatedServer = null;
        dedicatedServers.remove(0);
        try {
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
            }
            dedicatedServer.sendPass(nuevoRequest);
        }catch (NullPointerException e){
        }catch (IndexOutOfBoundsException e1){
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
