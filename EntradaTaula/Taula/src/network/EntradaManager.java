package network;

import controller.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class EntradaManager extends Thread {

    private static final String IP = "127.0.0.1";
    private final static int PORT = 12345;

    private Socket socket;
    private Controller controller;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private DataOutputStream dos;
    private boolean isRunning;

    public EntradaManager() throws IOException {
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

    public void sendRequest(String nameRequest, int quantity) throws IOException {
        dos.writeUTF("REQUEST-COMING");
        dos.writeUTF(nameRequest);
        dos.writeInt(quantity);
    }

    public void readUpdates() {
    }
}