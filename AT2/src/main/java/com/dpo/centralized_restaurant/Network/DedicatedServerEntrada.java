package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Model.Request.RequestManager;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedServerEntrada extends Thread{

    private RequestManager requestManager;

  private ArrayList<DedicatedServerEntrada> dedicatedServers;
    // DEDICATEDSERVER NO EXISTE
    // private ArrayList<DedicatedServer> dedicatedServers;

    private final Socket socket;
    private ObjectInputStream ois;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private DataOutputStream dos;

    public DedicatedServerEntrada(Socket socket, RequestManager requestsManager, ArrayList<DedicatedServerEntrada> dedicatedServers) {
        this.socket = socket;
        this.requestManager = requestsManager;

        //Add by: Marc --> arraylist of dedicatedServers to delete himself when connection close
        this.dedicatedServers = dedicatedServers;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String init;

            while (true) {
                init = dis.readUTF();
                switch (init) {


                }

            }

        } catch (IOException e) {
        } finally {
            try {
                ois.close();
            } catch (IOException e) {}
            try {
                oos.close();
            } catch (IOException e) {}
            try {
                dos.close();
            } catch (IOException e) {}
            try {
                dis.close();
            } catch (IOException e) {}
            try {
                socket.close();
                dedicatedServers.remove(this);
            } catch (IOException e) {}
        }
    }
}
