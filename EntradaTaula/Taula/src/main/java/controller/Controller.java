package controller;

import model.Dish;
import model.RequestDish;
import network.NetworkManager;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private final NetworkManager networkManager;
    private MainWindow vista;

    public Controller(MainWindow vista, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.vista = vista;
        createClock();
    }

    /**
     * According the which button is pressed it will do one action or another
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "TO-TAULA-PANEL":
                vista.changePanel("TO-TAULA-PANEL");
                break;
            case "TAULA-OPTIONS": //Haurem de demanar el menu
                networkManager.askOrders();
                vista.changeTaulaPanel("TAULA-OPTIONS");
                networkManager.askForMenu();
                break;
            case "TAULA-LOGIN":
                vista.changeTaulaPanel("TAULA-LOGIN");
                break;
            case "TAULA-ORDER":
                networkManager.askForMenu();
                vista.changeTaulaPanel("TAULA-ORDER");
                break;
            case "TAULA-SEE-ORDERS":
                networkManager.askOrders();
                vista.changeTaulaPanel("TAULA-SEE-ORDERS");
                break;
            case "TO-PAY":// Per dirigir-nos a la taula de pagament
                vista.seeBill(vista.getMyOrders());
                vista.changeTaulaPanel("TAULA-PAY");
                break;
            case "PAY-BILL":
                networkManager.payBill();
                break;
            case "BACK-TO-ORDERS":
                vista.changeTaulaPanel("TAULA-ORDER");
                break;
            case "SEND-COMANDA":
                sendComanda();
                break;
            case "REMOVE-FROM-ORDER":   //Eliminem el producte de la comanda
                vista.removeComandaFromCard(vista.getSelectedComanda(), this);
                break;
            case "REMOVE-COMANDA":
                vista.clearBagOfComandes();
                break;
            case "SEE-COMANDES":
                vista.changeTaulaPanel("BAG");
                break;
            case "BACK":
                vista.changeTaulaPanel("TAULA-OPTIONS");
                break;
            case "LOG IN":
                try {
                    networkManager.sendLogInRequest(vista.getRequestName(), vista.getPassword());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,
                            "There was a problem with the connection!",
                            "Connexion Error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                break;
            case "ORDER":
                vista.addComandaToCart(vista.getComandaToAdd(), this);
                break;
            case "DELETE-ORDER":
                try {
                    networkManager.sendDishToEliminate(vista.getDishToDelete());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "There was a problem with the connection!",
                            "Connexion Error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            break;
        }

    }

    /**
     * Creates de clock to check the current time
     */
    public void createClock() {
        Timer timer;
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.createClock();
            }
        };
        timer = new Timer(1000, actionListener);
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Given the menu of dishes, updates the view panel
     * @param menu
     */
    public void updateMenu(ArrayList<Dish> menu) {
        if (menu != null) {
            vista.updateMenu(menu);
            vista.registerController(this);
        } else {
            JOptionPane.showMessageDialog(vista,
                    "Cannot get the Menu, sorry!",
                    "Receiving Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the bill given the dishes have been asked for
     * @param bill
     */
    public void updateBill(ArrayList<RequestDish> bill) {
        if (bill != null) {
            vista.updateBill(bill);
            vista.registerController(this);
        } else {
            JOptionPane.showMessageDialog(vista,
                    "Cannot get the Orders, sorry!",
                    "Receiving Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Change the view panel if login its succesfull
     */
    public void correctLogin() {
        vista.changeTaulaPanel("TAULA-OPTIONS");
    }

    /**
     * Shows the following message if an error appears while removing a dish
     */
    public void errorDelete() {
        JOptionPane.showMessageDialog(vista,
                "Error when Removing Dish!",
                "Remove Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows the following message if the login its not succesfull
     */
    public void badLogin() {
        JOptionPane.showMessageDialog(vista,
                "Incorrect Login or Password, try again!",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Execute one line or the other according to the result of the payment
     * @param r
     */
    public void paymentResult(Boolean r) {
        if (r) {
            vista.changeTaulaPanel("TAULA-LOGIN");
        } else {
            vista.paymentDeclined();
        }
    }

    /**
     * Gest the info of a request and sends it to the server
     */
    private void sendComanda() {
        try {
            ArrayList<RequestDish> auxList = vista.getBagOfOrders();
            ArrayList<Dish> auxMenu = vista.getMenu();
            boolean flag = true;
            boolean found = false;
            for (RequestDish rd: auxList) {
                for (Dish d: auxMenu) {
                    if (d.getName().equalsIgnoreCase(rd.getName())) {
                        if (rd.getUnits() > d.getUnits()) {
                            flag = false;
                        }
                        found = true;
                    }
                }
                if (!found) {
                    flag = false;
                }
                found = false;
            }
            if (flag) {
                networkManager.sendComanda(vista.getBagOfOrders());
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Too many units requested! Request was cancelled",
                        "Units Error",
                        JOptionPane.ERROR_MESSAGE);
                vista.clearBagOfComandes();
            }
            } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "There was a problem with the connection!",
                    "Connexion Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Refreshes the bag of requests
     */
    public void resetComanda() {
        vista.clearBagOfComandes();
    }

    /**
     * Displays the following message if there was an error with the orders
     */
    public void badSending() {
        JOptionPane.showMessageDialog(vista,
                "Orders were not applied!",
                "Sending Error",
                JOptionPane.ERROR_MESSAGE);
    }

/*
    private ArrayList<RequestDish> rawData() {
        ArrayList<RequestDish> dishes = new ArrayList<>();
        RequestDish dish = new RequestDish(Long.MAX_VALUE, "Arros",10,5, 6,"");
        RequestDish dish1 = new RequestDish(Long.MAX_VALUE,"Patates fregides",10,3, 5,"");
        RequestDish dish2 = new RequestDish(Long.MAX_VALUE,"Llenguado",20,4, 4,"");
        RequestDish dish3 = new RequestDish(Long.MAX_VALUE, "Sopa",15,6, 3,"");
        RequestDish dish4 = new RequestDish(Long.MAX_VALUE, "Bacalla", 10.0,7, 2,"");

        dishes.add(dish);
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish4);
        return dishes;
    }
*/

}
