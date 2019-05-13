package view.Entrada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class RequestFrame extends JFrame {

    private RequestMenu jpRequestMenu;

    public RequestFrame() {

        this.setLayout(new GridLayout(1,1));
        this.setSize(400,480);

        /*····················   MENU    ···················· */
        jpRequestMenu = new RequestMenu();
        Color cAux = new Color(0x1A0D08);
        jpRequestMenu.setBackground(cAux);
        this.setBackground(cAux);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("ENTRADA");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpRequestMenu);
        this.setVisible(true);
    }

    public void changePanel (String which) {
        jpRequestMenu.changePanel(which);
    }

    public void registerController (ActionListener c) {
        jpRequestMenu.registerController(c);
    }

    public String getRequestName() {
        return jpRequestMenu.getRequestName();
    }

    public int getRequestQuantity() {
        return jpRequestMenu.getRequestQuantity();
    }

    public void setValuesToShow(String requestName, String password) {
        jpRequestMenu.setValuesToShow(requestName, password);
    }

    public void resetValues() {
        jpRequestMenu.resetValues();
    }
}
