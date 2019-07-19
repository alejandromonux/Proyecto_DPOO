package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;
import com.dpo.centralized_restaurant.database.ConectorDB;
import com.dpo.centralized_restaurant.database.DishServiceDB;
import com.dpo.centralized_restaurant.database.OrderService;
import com.dpo.centralized_restaurant.database.RequestService;

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
    private DishServiceDB dishS;
    private OrderService orderS;
    private RequestService requestS;

    private Controller controller;
    private boolean isRunning;
    private static ServerTaula serverTaula;

    public static ServerTaula getInstance() {
        if (serverTaula == null) {
            serverTaula = new ServerTaula();
        }
        return serverTaula;
    }

    public ServerTaula(){
        dedicatedServers = new ArrayList<>();
        isRunning  = true;
    }

    public ServerTaula(configJson config, ConectorDB conectorDB, Controller controller,
                       DishServiceDB dishS, OrderService orderS, RequestService requestS) {
        dedicatedServers = new ArrayList<>();
        serverSocket = null;
        requestsManager = new RequestManager();
        PORT = config.getPort_Taula();
        this.conectorDB = conectorDB;
        this.controller = controller;
        this.dishS = dishS;
        this.orderS = orderS;
        this.requestS = requestS;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;

            while (isRunning) {
                Socket socket = serverSocket.accept();  // Esperem a que algun usuari es connecti
                System.out.println("Connected Taula");
                //Modified by: Marc --> Added dedicatedServers in constructor
                DedicatedServerTaula dServer = new DedicatedServerTaula(socket, requestsManager, dedicatedServers, conectorDB,
                                                                        controller, dishS, orderS, requestS);   // Creem un cami dedicat a la connexio amb aquest usuari
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
     * Updates the orders setting a new activation date
     */
    public synchronized void updateOrders(){
        if(!dedicatedServers.isEmpty()){
            for(DedicatedServerTaula dst : dedicatedServers){
                dst.updateOrders();
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
            if (!serverSocket.isClosed())
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
