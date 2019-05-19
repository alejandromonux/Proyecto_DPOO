import com.google.gson.Gson;
import controller.Controller;
import model.config.configJSON;
import network.NetworkManager;
import view.MainWindow;

import javax.swing.*;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                configJSON config;
                try {
                    config = gson.fromJson(new FileReader("config.json"), configJSON.class);
                    NetworkManager networkManager = new NetworkManager(config);
                    MainWindow vista = new MainWindow();
                    Controller controller = new Controller(vista, networkManager);
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
