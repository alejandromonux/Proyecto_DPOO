package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Model.RequestManager;
import com.dpo.centralized_restaurant.Model.configJson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTaula extends Thread {

    private int PORT;
    private ServerSocket serverSocket;
    private final ArrayList<DedicatedServerTaula> dedicatedServers;
    private RequestManager requestsManager;

    public ServerTaula(configJson config) {
        dedicatedServers = new ArrayList<>();
        serverSocket = null;
        requestsManager = new RequestManager();
        PORT = config.getPort_Taula();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            boolean isRunning = true;

            while (isRunning) {
                Socket socket = serverSocket.accept();  // Esperem a que algun usuari es connecti
                System.out.println("Connected");
                //Modifyed by: Marc --> Added dedicatedServers in constructor
                DedicatedServerTaula dServer = new DedicatedServerTaula(socket, requestsManager, dedicatedServers);   // Creem un cami dedicat a la connexio amb aquest usuari
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

}
