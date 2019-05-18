package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.ModelDTO.ClientDTO;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Request.RequestDish;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;
import com.dpo.centralized_restaurant.Model.Worker;
import com.dpo.centralized_restaurant.database.ConectorDB;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles the connection between the system, client side, using sockets
 */
public class DedicatedServerTaula extends Thread{

    private RequestManager requestManager;
    private ArrayList<DedicatedServerTaula> dedicatedServers;
    private ConectorDB conectorDB;
    private Controller controller;

    private final Socket socket;
    private ObjectInputStream ois;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private DataOutputStream dos;

    private boolean start;

    /**
     * Prepares this part of the system to work along the rest of the system
     * @param socket
     * @param requestsManager
     * @param dedicatedServers
     * @param conectorDB
     * @param controller
     */
    public DedicatedServerTaula(Socket socket, RequestManager requestsManager, ArrayList<DedicatedServerTaula> dedicatedServers, ConectorDB conectorDB, Controller controller) {
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

            dis = new DataInputStream(socket.getInputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String init = "";
            while (start) {
                init = dis.readUTF();
                switch (init) {
                    case "LOGIN-REQUEST":
                        loginRequest();
                        break;
                    case "DISHES-COMING":
                        int counter = dis.readInt();
                        ArrayList<RequestDish> comanda = new ArrayList<>();
                        try {
                            while (counter-- > 0) {
                                comanda.add((RequestDish) ois.readObject());
                            }

                        } catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }

                        long idMesaAfectadaOrder = dis.readLong();
                        break;
                    case "ELIMINATE-DISH":
                        String dishToEliminate = dis.readUTF();
                        long idMesaAfectadaEliminate = dis.readLong();
                        break;
                    case "SEE-MENU":
                        dos.writeUTF("UPDATE-MENU");
                        break;
                    case "PAY-BILL":
                        doPayment();
                        break;
                }

                init = "";

            }

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    public void loginRequest() {

        try {
            String requestName = dis.readUTF();
            String password = dis.readUTF();

            Request rAux = conectorDB.loginRequest(requestName, password);
            if ( rAux == null) {
                    dos.writeUTF("LOGIN-INCORRECT");
            } else {
                dos.writeUTF("LOGIN-CORRECT");
                oos.writeObject(rAux);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doPayment() throws IOException, ClassNotFoundException {
        Request inRequest = (Request)ois.readObject();
        Request newR = conectorDB.payBill(inRequest);
        if (newR != null) {
            if(newR.getId() != -1) {
                controller.informarEntrada(newR);
            }
             dos.writeUTF("PAYMENT-ACCEPTED");
            } else {
            dos.writeUTF("PAYMENT-DECLINED");
        }
    }

    /**
     * Sends all the requests trough the connection between server and client
     */
    public void closeDedicatedServer(){
        start = false;
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
    }
}
