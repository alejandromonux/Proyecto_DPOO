package network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import controller.Controller;
import model.Dish;
import model.Request;
import model.RequestDish;
import model.config.configJSON;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
    }

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

    public boolean sendDishToEliminate(String dish) throws IOException {
        dos.writeUTF("ELIMINATE-DISH");
        dos.writeUTF(dish);
        dos.writeUTF(myRequest.getMesa_name());
        return dis.readBoolean();
    }

    public void askForMenu() {
        try {
            dos.writeUTF("SEE-MENU");
        } catch (IOException e) {
            controller.updateMenu(null);
        }
    }

    public void askOrders() {
        try {
            dos.writeUTF("SEE-MY-ORDERS");
        } catch (IOException e) {
            controller.updateBill(null);
        }
    }

    public void sendLogInRequest(String requestName, String password)throws IOException, ClassNotFoundException {
        dos.writeUTF("LOGIN-REQUEST");
        dos.writeUTF(requestName);
        dos.writeUTF(password);

        String response = dis.readUTF();
        if (response.equalsIgnoreCase("LOGIN-CORRECT")) {
            String json = dis.readUTF();
            Gson g = new Gson();
            myRequest = g.fromJson(json, Request.class);
            controller.correctLogin();
        } else {
            if (response.equalsIgnoreCase("LOGIN-INCORRECT")) {
                controller.badLogin();
            }
        }
    }

    public void payBill() {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(myRequest);
            dos.writeUTF("PAY-BILL");
            dos.writeUTF(json);
            Boolean result = dis.readBoolean();
            controller.paymentResult(result);
            if (!result) {
                myRequest = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendComanda(ArrayList<RequestDish> comanda) throws IOException {

        dos.writeUTF("DISHES-COMING");
        dos.writeInt(comanda.size());
        for (RequestDish rd: comanda) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(rd);
            dos.writeUTF(json);
        }
        if (dis.readBoolean()) { // Introduits correctament
            controller.resetComanda();
        } else {
            controller.badSending();
        }
        return false;
    }


    public void readUpdates() {
        try {

            switch (dis.readUTF()) {
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
            }

        } catch (Exception e) {
            try {
                dos.close();
                dis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
