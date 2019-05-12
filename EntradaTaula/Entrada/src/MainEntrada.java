import controller.Controller;
import network.EntradaManager;
import network.NetworkManager;
import view.Entrada.ListFrame;
import view.Entrada.RequestMenu;
import view.MainWindow;

import javax.swing.*;

public class MainEntrada {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    EntradaManager networkManager = new EntradaManager();
                    MainWindow vista = new MainWindow();
                    ListFrame listFrame = new ListFrame(vista);
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
