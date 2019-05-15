package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;
import com.dpo.centralized_restaurant.database.ConectorDB;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedServerEntrada extends Thread{

    private RequestManager requestManager;
    private ArrayList<DedicatedServerEntrada> dedicatedServers;
    private ConectorDB conectorDB;
    private Controller controller;


    private final Socket socket;
    private ObjectInputStream ois;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private DataOutputStream dos;

    /**
     * Prepares this part of the system to work along the rest of the system
     * @param socket
     * @param requestsManager
     * @param dedicatedServers
     * @param conectorDB
     * @param controller
     */
    public DedicatedServerEntrada(Socket socket, RequestManager requestsManager, ArrayList<DedicatedServerEntrada> dedicatedServers, ConectorDB conectorDB, Controller controller) {
        this.socket = socket;
        this.requestManager = requestsManager;

        //Add by: Marc --> arraylist of dedicatedServers to delete himself when connection close
        this.dedicatedServers = dedicatedServers;
        this.conectorDB = conectorDB;
        this.controller = controller;
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
                    case "REQUEST-COMING":
                        String nameNew = dis.readUTF();
                        int cantidadPersonas = dis.readInt();
                        dos.writeBoolean(conectorDB.insertRequest(nameNew, cantidadPersonas));

                        break;

                    case "NEED-REQUEST-LIST":
                        dos.writeUTF("UPDATE-REQUEST-LIST");
                        ArrayList<String> envia = conectorDB.getRequests();
                        dos.writeInt(envia.size());
                        for (int j = 0; j < envia.size(); j++){
                            dos.writeUTF(envia.get(j));
                        }
                        break;

                    case "DELETE-REQUEST":
                        String nameToCancel = dis.readUTF();
                        dos.writeBoolean(conectorDB.deleteRequest(nameToCancel));
                        break;

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
            //try {
                dedicatedServers.remove(this);
                //socket.close();  --Marc: No se si esto deberia estar ya que se cierra a el mismo
            //} catch (IOException e) {}
        }
    }


}
