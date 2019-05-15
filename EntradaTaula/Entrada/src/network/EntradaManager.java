package network;

import controller.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

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
            try {
                readUpdates();// Estem sempre a l'espera de rebre actualizacions.
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Es aixi perque en haver-hi diversos usuaris, si algu envia nova informacio
            // volem obtenir aquesta actualitzacio gracies a un broadcast
        }
    }

    public void sendRequest(String nameRequest, int quantity) throws IOException {
        dos.writeUTF("REQUEST-COMING");
        dos.writeUTF(nameRequest);
        dos.writeInt(quantity);
        if (!dis.readBoolean()){
            controller.insertNotification();
        }
    }

    public void askRequests() throws IOException {
        dos.writeUTF("NEED-REQUEST-LIST");

    }

    public void readUpdates() throws IOException {
        String inDuty = dis.readUTF();

        switch (inDuty) {
            case "UPDATE-REQUEST-LIST":
                ArrayList<String> requests = new ArrayList<>();
                int size = dis.readInt();
                while (size > 0) {
                    requests.add(dis.readUTF());
                    size--;
                }
                controller.updateRequestList(requests);
                break;
            case "INCOMING-PASSWORD":
                String requestName = dis.readUTF();
                String requestPassword = dis.readUTF();
                controller.showPassword(requestName, requestPassword);
                break;
        }

    }

    public boolean deleteRequest(String requestName) throws IOException {
        dos.writeUTF("DELETE-REQUEST");
        dos.writeUTF(requestName);
        return dis.readBoolean();
    }
}