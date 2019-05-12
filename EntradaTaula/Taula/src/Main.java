import controller.Controller;
import network.NetworkManager;
import view.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //NetworkManager networkManager = new NetworkManager();
                    MainWindow vista = new MainWindow();
                    Controller controller = new Controller(vista, null);
                    vista.registerController(controller);
                    vista.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "CRestaurant may not be opened yet!",
                            "Connexion Error",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}
