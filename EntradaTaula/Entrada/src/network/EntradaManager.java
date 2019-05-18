package network;

import controller.Controller;
import model.Request;
import model.config.configJSON;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class EntradaManager extends Thread {

    private String IP;
    private int PORT;

    private Socket socket;
    private Controller controller;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning;

    public EntradaManager(configJSON config) throws IOException {
        controller = null;
        IP = config.getIP();
        PORT = config.getPort_Entrada();
        socket = new Socket(IP, PORT);
        dis = new DataInputStream(socket.getInputStream());
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
                isRunning = false;
                try {
                    dos.close();
                    dis.close();
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            // Es aixi perque en haver-hi diversos usuaris, si algu envia nova informacio
            // volem obtenir aquesta actualitzacio gracies a un broadcast
        }
    }

    public void sendRequest(String nameRequest, int quantity) throws IOException {
        dos.writeUTF("REQUEST-COMING");
        dos.writeUTF(nameRequest);
        dos.writeInt(quantity);
    }

    public void askRequests() throws IOException {
        dos.writeUTF("NEED-REQUEST-LIST");

    }

    public void readUpdates() throws IOException {
        String inDuty = dis.readUTF();

        switch (inDuty) {
            case "UPDATE-REQUEST-LIST":
                ArrayList<Request> requests = new ArrayList<>();
                int size = dis.readInt();
                while (size > 0) {
                    int id = dis.readInt();
                    String name = dis.readUTF();
                    name = name.equals("NULL") ? null: name;
                    String pass = dis.readUTF();
                    pass = pass.equals("NULL") ? null: pass;
                    requests.add(new Request(name, id, pass));
                    size--;
                }
                controller.updateRequestList(requests);
                break;
            case "INCOMING-ASSIGNMENT":
                    int id = dis.readInt();
                System.out.printf("mecago en mi puta madre ya ostias");
                    String name = dis.readUTF();
                System.out.println("dnf,zsdnsnd");
                    String pass = dis.readUTF();
                    System.out.println("joooljlsjd");
                    if (name.equals("NO SE HA ENCONTRADO MESA")){
                        controller.notificationComanda(id);
                    }
                        //mostrar error, actualizar lista

                    dos.writeUTF("NEED-REQUEST-LIST");
                    controller.showPassword(name, pass);

                break;
            case "REQUEST-COMING":
                boolean done = dis.readBoolean();
                if (!done){
                    controller.insertNotification();
                }
                break;
        }

    }

    public boolean deleteRequest(String requestName) throws IOException {
        dos.writeUTF("DELETE-REQUEST");
        dos.writeUTF(requestName);
        return dis.readBoolean();
    }
}
