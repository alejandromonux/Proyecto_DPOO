package view;

import controller.Controller;
import model.Dish;
import view.Taula.TaulaGeneralPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JFrame {


    private CardLayout cardLayout;
    private JPanel jpContent;

    private TaulaGeneralPanel userOptionsMenu;  // Taula

    public MainWindow() {

        this.setLayout(new BorderLayout());
        this.setSize(800,380);

        //this.setBackground(new Color(0x914225));

        userOptionsMenu = new TaulaGeneralPanel();

        cardLayout = new CardLayout();
        jpContent = new JPanel();
        jpContent.setLayout(cardLayout);
        jpContent.add("TO-TAULA-PANEL", userOptionsMenu);

        this.add(jpContent);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("TAULA");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void changePanel(String which) {

        if (which.equals("TO-TAULA-PANEL")) {
            this.setSize(800,380);
            this.setLocationRelativeTo(null);
            this.setTitle("TAULA");
        } else {
            this.setSize(400,480);
            this.setLocationRelativeTo(null);
            this.setTitle("ENTRADA");
        }
        cardLayout.show(jpContent, which);
    }

    public void registerController(Controller c) {
        userOptionsMenu.registerController(c);
    }

    public void changeTaulaPanel(String which) {
        userOptionsMenu.changePanel(which);
    }

    public String getRequestName() {
        return userOptionsMenu.getRequestName();
    }

    public String getPassword() {
        return userOptionsMenu.getPassword();
    }

    public String getSelectedDish()
    {
        return userOptionsMenu.getSelectedDish();
    }

    public String getDishToDelete() {
        return userOptionsMenu.getDishToDelete();
    }

    public void updateMenu(ArrayList<Dish> menu) {
        userOptionsMenu.updateMenu(menu);
    }

    public void updateBill(ArrayList<Dish> bill) {
        userOptionsMenu.updateBill(bill);
    }
}
