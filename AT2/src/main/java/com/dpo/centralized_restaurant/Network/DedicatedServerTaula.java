package com.dpo.centralized_restaurant.Network;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.ModelDTO.ClientDTO;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Request.RequestDish;
import com.dpo.centralized_restaurant.Model.Request.RequestManager;
import com.dpo.centralized_restaurant.Model.Worker;
import com.dpo.centralized_restaurant.database.ConectorDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

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
    private DataInputStream dis;
    private DataOutputStream dos;

    private Request requestActual;

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
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (Exception e){}

        start = true;
        requestActual = null;
    }

    @Override
    public void run() {
        try {

            String init = "";
            while (start) {
                init = dis.readUTF();
                switch (init) {
                    case "LOGIN-REQUEST":
                        loginRequest();
                        break;
                    case "DISHES-COMING":
                        dishesComing();
                        updateDishesToAll();
                        break;
                    case "ELIMINATE-DISH":
                        String dishToEliminate = dis.readUTF();
                        Gson g = new Gson();
                        RequestDish rdAux = g.fromJson(dishToEliminate, RequestDish.class);
                        int requestId = dis.readInt();
                        if(!conectorDB.deleteComanda(rdAux, requestId)) {
                            dos.writeUTF("ORDER-DELETE-BAD");
                        } else {
                            dos.writeUTF("ORDER-DELETE-CORRECT");
                            updateDishesToAll();
                        }
                        break;
                    case "SEE-MENU":
                        ArrayList<Dish> menu = conectorDB.findActiveDishes();
                        dos.writeUTF("UPDATE-MENU");
                        dos.writeInt(menu.size());
                        for (Dish d: menu) {
                            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                            String jsonRequest = ow.writeValueAsString(d);
                            dos.writeUTF(jsonRequest);
                        }
                        break;
                    case "SEE-MY-ORDERS":
                        ArrayList<RequestDish> comandaOut = conectorDB.getMyOrders(requestActual.getId());
                        dos.writeUTF("UPDATE-CLIENT-ORDERS");
                        dos.writeInt(comandaOut.size());
                        for (RequestDish d: comandaOut) {
                            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                            String jsonRequest = ow.writeValueAsString(d);
                            dos.writeUTF(jsonRequest);
                        }
                        break;
                    case "PAY-BILL":
                        doPayment();
                        break;
                }

                init = "";

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
                //socket.close(); --Marc: Dani esto no se si se deberia hacer.
            //} catch (IOException e) {}
        }
    }

    /**
     * Reads and handles the request to login to the system, given the name and the password
     */
    public void loginRequest() {

        try {
            String requestName = dis.readUTF();
            String password = dis.readUTF();
            Request rAux = conectorDB.loginRequest(requestName, password);
            if ( rAux != null) {
                dos.writeUTF("LOGIN-CORRECT");
                requestActual = rAux;
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String jsonRequest = ow.writeValueAsString(requestActual);
                dos.writeUTF(jsonRequest);
            } else {
                dos.writeUTF("LOGIN-INCORRECT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the menu with the current active dishes
     */
    public void updateDishesToAll(){
        ArrayList<Dish> listaPlatos = conectorDB.findActiveDishes();

        for (DedicatedServerTaula dst : dedicatedServers){
            dst.updateMenu(listaPlatos);
        }
    }

    /**
     * Given the list of the dishes above, updates the menu accordingly
     * @param menu
     */
    public void updateMenu(ArrayList<Dish> menu){
        try {
            dos.writeUTF("UPDATE-MENU");

            dos.writeInt(menu.size());
            for (Dish d : menu) {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String jsonRequest = ow.writeValueAsString(d);
                dos.writeUTF(jsonRequest);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows the client to finish the payment and update the info in the database
     */
    public void doPayment() {
        try {
            Gson g = new Gson();
            Request inRequest = g.fromJson(dis.readUTF(), Request.class);
            Request newR = conectorDB.payBill(inRequest);
            if (newR != null) {
                dos.writeUTF("PAYMENT-ACCEPTED");
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(newR);
                dos.writeUTF(json);
                controller.informarEntrada(newR);
            } else {
                dos.writeUTF("PAYMENT-DECLINED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepares the system ro recieve a number of requests and add them to the database
     * @throws IOException
     */
    public void dishesComing() throws IOException {
        try {
            int counter = dis.readInt();
            ArrayList<RequestDish> comanda = new ArrayList<>();
            Gson g = new Gson();
            while (counter-- > 0) {
                RequestDish rAux = g.fromJson(dis.readUTF(), RequestDish.class);
                comanda.add(rAux);
            }
            conectorDB.insertComanda(comanda);
            dos.writeUTF("COMANDA-INSERT-OKEY");

        } catch (Exception e){
            dos.writeUTF("COMANDA-INSERT-BAD");
            e.printStackTrace();
        }

        //long idMesaAfectadaOrder = dis.readLong();
    }


    /**
     * Sends all the requests trough the connection between server and client
     */
    public void closeDedicatedServer(){
        start = false;
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
