package network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import controller.Controller;
import model.Dish;
import model.Request;
import model.RequestDish;
import model.config.configJSON;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager extends Thread {

    private String IP;
    private int PORT;

    private Socket socket;
    private Controller controller;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning;

    private Request myRequest;

    public NetworkManager(configJSON config) throws IOException {
        controller = null;
        IP = config.getIP();
        PORT = config.getPort_Entrada();
        socket = new Socket(IP, PORT);
        InputStream inputStream = socket.getInputStream();
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(inputStream);
    }

    /**
     * Starts the void run and thus the connection with the server
     * @param controller
     */
    public void startServerConnection(Controller controller) {
        this.controller = controller;
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        while (isRunning) {
            readUpdates();      // Estem sempre a l'espera de rebre actualizacions.
            // Es aixi perque en haver-hi diversos usuaris, si algu envia nova informacio
            // volem obtenir aquesta actualitzacio gracies a un broadcast
        }
    }

    public void sendRequestedDish(String dish) throws IOException {
        dos.writeUTF("DISH-ORDER-COMING");
        //dos.writeUTF();
        dos.writeUTF(dish);
    }

    /**
     * Sends a dish to the server to be removed
     * @param dish
     * @throws IOException
     */
    public void sendDishToEliminate(RequestDish dish) throws IOException {
        dos.writeUTF("ELIMINATE-DISH");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonDish = ow.writeValueAsString(dish);
        dos.writeUTF(jsonDish);
        dos.writeInt(myRequest.getId());
    }

    /**
     * Recieves the menu with the dishes
     */
    public void askForMenu() {
        try {
            dos.writeUTF("SEE-MENU");
        } catch (IOException e) {
            controller.updateMenu(null);
        }
    }

    /**
     * Recieves the different orders
     */
    public void askOrders() {
        try {
            dos.writeUTF("SEE-MY-ORDERS");
        } catch (IOException e) {
            controller.updateBill(null);
        }
    }

    /**
     * Send the log in information to be validated
     * @param requestName
     * @param password
     * @throws IOException
     */
    public void sendLogInRequest(String requestName, String password)throws IOException {
        dos.writeUTF("LOGIN-REQUEST");
        dos.writeUTF(requestName);
        dos.writeUTF(password);
    }

    /**
     * Sends the information about the bill to be paid
     */
    public void payBill() {
        try {
            myRequest.setInService(2);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(myRequest);
            dos.writeUTF("PAY-BILL");
            dos.writeUTF(json);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "There was a problem with the connection!",
                    "Connexion Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Sends a request
     * @param comanda
     * @return
     * @throws IOException
     */
    public boolean sendComanda(ArrayList<RequestDish> comanda) throws IOException {

        dos.writeUTF("DISHES-COMING");
        dos.writeInt(comanda.size());
        for (RequestDish rd: comanda) {
            rd.setRequest_id(myRequest.getId());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(rd);
            dos.writeUTF(json);
        }
        return false;
    }

    /**
     * Gets the latest updates about the information that holds the server
     */
    public void readUpdates() {
        try {
            String textIn = dis.readUTF();
            switch (textIn) {
                case "UPDATE-MENU":
                        int cmpt = dis.readInt();   // Rebem la quantitat de Dishes que ens enviaran
                        ArrayList<Dish> menu = new ArrayList<Dish>();
                        while (cmpt > 0) {
                            String json = dis.readUTF();
                            Gson g = new Gson();
                            Dish dAux = g.fromJson(json, Dish.class);
                            menu.add(dAux);
                            cmpt--;
                        }
                        controller.updateMenu((ArrayList<Dish>) menu);
                        break;
                case "UPDATE-CLIENT-ORDERS":            // El servidor ens envia tots els plats que ha demanat la reserva.
                        int compt2 = dis.readInt();   // Rebem la quantitat de Dishes que ens enviaran
                        ArrayList<RequestDish> bill = new ArrayList<RequestDish>();
                        while (compt2 > 0) {
                            String json = dis.readUTF();
                            Gson g = new Gson();
                            RequestDish rd = g.fromJson(json, RequestDish.class);
                            bill.add(rd);
                            compt2--;
                        }
                        controller.updateBill((ArrayList<RequestDish>) bill);
                    break;

                case "LOGIN-CORRECT":
                    String json = dis.readUTF();
                    System.out.println(json);
                    Gson g = new Gson();
                    Request rAux = g.fromJson(json, Request.class);
                    myRequest = rAux;
                    controller.correctLogin();
                    break;

                case "LOGIN-INCORRECT":
                    controller.badLogin();
                    break;
                case "COMANDA-INSERT-OKEY":
                        controller.resetComanda();
                    break;
                case "COMANDA-INSERT-BAD":
                    controller.badSending();
                    break;
                case "ORDER-DELETE-BAD":
                    controller.errorDelete();
                    break;
                case "ORDER-DELETE-CORRECT":
                    askOrders();
                    break;
                case "PAYMENT-ACCEPTED":
                    controller.paymentResult(true);
                    myRequest = null;
                    Gson g2 = new Gson();
                    myRequest = g2.fromJson(dis.readUTF(), Request.class);
                    break;
                case "PAYMENT-DECLINED":
                    controller.paymentResult(false);
                    break;
            }
            textIn = "";

        } catch (IOException e) {
            try {
                dos.close();
                dis.close();
                if (!socket.isClosed())
                socket.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "There was a problem with the connection!",
                        "Connexion Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
}
