package network;

import controller.Controller;
import model.Request;
import model.config.configJSON;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
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
        //configuracio del socket
        IP = config.getIP();
        PORT = config.getPort_Entrada();
        socket = new Socket(IP, PORT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Start the run void and thus the connection with the server
     * @param controller
     */
    public void startServerConnection(Controller controller) {
        this.controller = controller;
        isRunning = true;
        start();
        //engeguem el thread de lectura
    }

    @Override
    public void run() {
        try {
            askRequests();
        } catch (IOException e) {
            controller.errorConexio();
        }
        while (isRunning) {
            try {
                readUpdates();// Estem sempre a l'espera de rebre actualizacions.
            } catch (Exception e) {
                isRunning = false;
                try {
                    dos.close();
                    dis.close();
                    if (!socket.isClosed())
                    socket.close();
                    controller.errorConexio();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            // Es aixi perque en haver-hi diversos usuaris, si algu envia nova informacio
            // volem obtenir aquesta actualitzacio gracies a un broadcast
        }
    }

    /**
     * Send a request to the server
     * @param nameRequest
     * @param quantity
     * @throws IOException
     */
    public void sendRequest(String nameRequest, int quantity) throws IOException {
        //enviar requests nou
        dos.writeUTF("REQUEST-COMING");
        dos.writeUTF(nameRequest);
        dos.writeInt(quantity);

    }

    /**
     * Gets the group of requests from the server
     * @throws IOException
     */
    public void askRequests() throws IOException {
        //demana llista de requests
        dos.writeUTF("NEED-REQUEST-LIST");

    }

    /**
     * Get the latest information that might have changed in the server
     * @throws IOException
     */
    public void readUpdates() throws IOException {
        String inDuty = dis.readUTF();
        switch (inDuty) {
            //update de la list de requests
            case "UPDATE-REQUEST-LIST":
                ArrayList<Request> requests = new ArrayList<>();
                int size = dis.readInt();
                while (size > 0) {
                    //lectura de dades
                    int id = dis.readInt();
                    String name = dis.readUTF();
                    name = name.equals("NULL") ? "---": name;
                    String pass = dis.readUTF();
                    pass = pass.equals("NULL") ? "---": pass;
                    String mesa = dis.readUTF();
                    mesa = mesa.equals("NULL") ? "---": mesa;
                    requests.add(new Request(name, id, pass, mesa));

                    size--;
                }
                    controller.updateRequestList(requests, controller);
                break;
            case "INCOMING-ASSIGNMENT":
                    //assignacio d'una request i mostrar contrasenya
                    int id = dis.readInt();
                    String name = dis.readUTF();
                    String pass = dis.readUTF();
                    String mesa = dis.readUTF();
                    if (name.equals("NO SE HA ENCONTRADO MESA")){
                        //solucio errors
                        controller.notificationComanda(id);
                    }else{
                        //mostrar contrassnya
                        dos.writeUTF("NEED-REQUEST-LIST");
                        controller.showPassword(name, pass);
                        controller.registerControllers();
                    }
                    dos.writeUTF("UPDATE-REQUEST-LIST");
                        //mostrar error, actualizar lista
                break;
            case "REQUEST-COMING":
                //comprobacio de la requests
                boolean done = dis.readBoolean();
                if (!done){
                    controller.insertNotification();
                }
                break;
            case "DELETE-RESPONSE":
                //comprobacio de delete correctament
                boolean dona = dis.readBoolean();
                if (!dona){
                    controller.errorConexio();
                }
                break;
        }

    }

    /**
     * Deletes a request given its name
     * @param requestName
     * @throws IOException
     */
    public void deleteRequest(String requestName) throws IOException {
        //eliminar requests
        dos.writeUTF("DELETE-REQUEST");
        dos.writeUTF(requestName);
    }
}
