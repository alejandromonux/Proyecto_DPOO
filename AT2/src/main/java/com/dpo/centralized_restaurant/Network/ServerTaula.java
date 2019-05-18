package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;
import com.dpo.centralized_restaurant.database.ConectorDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *  Handles the connection between the system, client side, with the general server and the database
 */
public class ServerTaula extends Thread {

    private int PORT;
    private ServerSocket serverSocket;
    private final ArrayList<DedicatedServerTaula> dedicatedServers;
    private RequestManager requestsManager;
    private ConectorDB conectorDB;
    private Controller controller;
    private boolean isRunning;

    public ServerTaula(configJson config, ConectorDB conectorDB, Controller controller) {
        dedicatedServers = new ArrayList<>();
        serverSocket = null;
        requestsManager = new RequestManager();
        PORT = config.getPort_Taula();
        this.conectorDB = conectorDB;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;

            while (isRunning) {
                Socket socket = serverSocket.accept();  // Esperem a que algun usuari es connecti
                System.out.println("Connected Taula");
                //Modifyed by: Marc --> Added dedicatedServers in constructor
                DedicatedServerTaula dServer = new DedicatedServerTaula(socket, requestsManager, dedicatedServers, conectorDB, controller);   // Creem un cami dedicat a la connexio amb aquest usuari
                dedicatedServers.add(dServer);
                dServer.start();
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
     * Finishes the connection between the system from the Client side
     */
    public void closeServer(){
        isRunning = false;

        for(DedicatedServerTaula dst : dedicatedServers){
            dst.closeDedicatedServer();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
