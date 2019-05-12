package view;

import controller.Controller;
import model.Dish;
import view.Entrada.RequestMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JFrame {


    private CardLayout cardLayout;
    private JPanel jpContent;

    private RequestMenu requestMenu;       // Entrada

    public MainWindow() {

        this.setLayout(new BorderLayout());
        this.setSize(400,480);
        //this.setBackground(new Color(0x914225));

        requestMenu = new RequestMenu();

        cardLayout = new CardLayout();
        jpContent = new JPanel();
        jpContent.setLayout(cardLayout);
        jpContent.add("TO-ENTRADA-PANEL", requestMenu);

        this.add(jpContent);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("ENTRADA");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void changePanel(String which) {
        cardLayout.show(jpContent, which);
    }

    public void registerController(Controller c) {
        requestMenu.registerController(c);
    }


    public String getRequestName() {
        return requestMenu.getRequestName();
    }

    public int getRequestQuantity() {
        return requestMenu.getRequestQuantity();
    }
}
