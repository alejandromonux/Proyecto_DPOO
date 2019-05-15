import controller.Controller;
import model.config.configJSON;
import network.EntradaManager;
import view.Entrada.ListFrame;
import view.Entrada.RequestFrame;

import javax.swing.*;
import java.io.FileReader;

public class MainEntrada {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                configJSON config;
                try {
                    config = gson.fromJson(new FileReader("config.json"), configJson.class);
                    EntradaManager networkManager = new EntradaManager();
                    RequestFrame vista = new RequestFrame();
                    ListFrame listFrame = new ListFrame(-50 + vista.getWidth() , vista.getY());
                    listFrame.setVisible(true);
                    Controller controller = new Controller(vista, listFrame, networkManager);
                    networkManager.startServerConnection(controller);
                    vista.registerController(controller);
                    vista.setVisible(true);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "CRestaurant may not be opened yet!",
                            "Connexion Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
