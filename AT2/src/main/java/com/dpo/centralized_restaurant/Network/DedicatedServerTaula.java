package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Model.ModelDTO.ClientDTO;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedServerTaula extends Thread{

    private RequestManager requestManager;

    private ArrayList<DedicatedServerTaula> dedicatedServers;

    private final Socket socket;
    private ObjectInputStream ois;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private DataOutputStream dos;

    public DedicatedServerTaula(Socket socket, RequestManager requestsManager, ArrayList<DedicatedServerTaula> dedicatedServers) {
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
            String init = "";
            while (true) {
                init = dis.readUTF();
                switch (init) {
                    case "USER-LOGIN":
                        try {
                            ClientDTO loginClient = (ClientDTO) ois.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "DISH-ORDER-COMING":
                        String dishOrdered = dis.readUTF();
                        long idMesaAfectadaOrder = dis.readLong();
                        break;

                    case "ELIMINATE-DISH":
                        String dishToEliminate = dis.readUTF();
                        long idMesaAfectadaEliminate = dis.readLong();

                        break;
                    case "SEE-MENU":

                        dos.writeUTF("UPDATE-MENU");

                        break;
                    default:

                        break;
                }

                init = "";

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
            //try {
                dedicatedServers.remove(this);
                //socket.close(); --Marc: Dani esto no se si se deberia hacer.
            //} catch (IOException e) {}
        }
    }
}
