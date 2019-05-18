package network;

import controller.Controller;
import model.Dish;
import model.RequestDish;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager extends Thread {

    private static final String IP = "127.0.0.1";
    private final static int PORT = 12345;

    private Socket socket;
    private Controller controller;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private DataOutputStream dos;
    private boolean isRunning;

    public NetworkManager() throws IOException {
        controller = null;
        socket = new Socket(IP, PORT);
        dis = new DataInputStream(socket.getInputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
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

    public void sendDishToEliminate(String dish) throws IOException {
        dos.writeUTF("ELIMINATE-DISH");
        //dos.writeUTF();
        dos.writeUTF(dish);
    }

    public void askForMenu() throws IOException {
        dos.writeUTF("SEE-MENU");
    }

    public void sendLogInRequest(String requestName, String password)throws IOException {
        dos.writeUTF("LOGIN-REQUEST");
        dos.writeUTF(requestName);
        dos.writeUTF(password);
    }

    public void readUpdates() {
        try {
            switch (dis.readUTF()) {
                case "UPDATE-MENU":
                        int cmpt = dis.readInt();   // Rebem la quantitat de Dishes que ens enviaran
                        ArrayList<RequestDish> menu = new ArrayList<>();
                        while (cmpt > 0) {
                            menu.add((RequestDish)ois.readObject());
                            cmpt--;
                        }
                        controller.updateMenu((ArrayList<RequestDish>) menu);
                        break;
                case "UPDATE-CLIENT-ORDERS":            // El servidor ens envia tots els plats que ha demanat la reserva.
                        int compt2 = dis.readInt();   // Rebem la quantitat de Dishes que ens enviaran
                        ArrayList<RequestDish> bill = new ArrayList<>();
                        while (compt2 > 0) {
                            bill.add((RequestDish)ois.readObject());
                            compt2--;
                        }
                        controller.updateBill((ArrayList<RequestDish>) bill);
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
