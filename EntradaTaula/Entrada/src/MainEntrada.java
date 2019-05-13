import controller.Controller;
import network.EntradaManager;
import network.NetworkManager;
import view.Entrada.ListFrame;
import view.Entrada.RequestFrame;
import view.Entrada.RequestMenu;

import javax.swing.*;

public class MainEntrada {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
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
