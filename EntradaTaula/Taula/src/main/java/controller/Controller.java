package controller;

import model.Dish;
import model.Request;
import model.RequestDish;
import network.NetworkManager;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Controller implements ActionListener {

    private final NetworkManager networkManager;
    private MainWindow vista;

    public Controller(MainWindow vista, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.vista = vista;
        createClock();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "TO-TAULA-PANEL":
                vista.changePanel("TO-TAULA-PANEL");
                break;
            case "TAULA-OPTIONS": //Haurem de demanar el menu
                vista.changeTaulaPanel("TAULA-OPTIONS");
                networkManager.askForMenu();
                break;
            case "TAULA-LOGIN":
                vista.changeTaulaPanel("TAULA-LOGIN");
                break;
            case "TAULA-ORDER":
                vista.changeTaulaPanel("TAULA-ORDER");
                break;
            case "TAULA-SEE-ORDERS":
                networkManager.askOrders();
                vista.changeTaulaPanel("TAULA-SEE-ORDERS");
                break;
            case "TO-PAY":// Per dirigir-nos a la taula de pagament
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "ORDER":
                vista.addComandaToCart(vista.getComandaToAdd(), this);
                break;
            case "DELETE-ORDER":
                try {
                    System.out.println(vista.getDishToDelete());
                    if (!networkManager.sendDishToEliminate(vista.getDishToDelete())) {
                        JOptionPane.showMessageDialog(vista,
                                "Error when Removing Dish!",
                                "Remove Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

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

    public void correctLogin() {
        vista.changeTaulaPanel("TAULA-OPTIONS");
    }

    public void badLogin() {
        JOptionPane.showMessageDialog(vista,
                "Incorrect Login or Password, try again!",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void paymentResult(Boolean r) {
        if (r) {
            vista.changePanel("TAULA-LOGIN");
        } else {
            vista.paymentDeclined();
        }
    }

    private void sendComanda() {
        try {
            networkManager.sendComanda(vista.getBagOfOrders());
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void resetComanda() {
        vista.clearBagOfComandes();
    }

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
