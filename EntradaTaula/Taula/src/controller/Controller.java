package controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.Dish;
import model.RequestDish;
import network.NetworkManager;
import view.MainWindow;

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "TO-TAULA-PANEL":
                vista.changePanel("TO-TAULA-PANEL");
                break;
            case "TAULA-OPTIONS":
                vista.changeTaulaPanel("TAULA-OPTIONS");
                break;
            case "TAULA-LOGIN":
                vista.changeTaulaPanel("TAULA-LOGIN");
                break;
            case "TAULA-ORDER":
                vista.changeTaulaPanel("TAULA-ORDER");
                break;
            case "TAULA-SEE-ORDERS":
                vista.changeTaulaPanel("TAULA-SEE-ORDERS");
                break;
            case "TO-PAY":// Per dirigir-nos a la taula de pagament
                vista.changeTaulaPanel("TAULA-PAY");
                break;
            case "PAY-BILL":
                //TODO: let the server know this client wants to finish its service
                break;
            case "BACK-TO-ORDERS":
                vista.changeTaulaPanel("TAULA-ORDER");
                break;
            case "SEND-COMANDA":
                sendComanda();
                break;
            case "REMOVE-FROM-ORDER":
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
                    vista.changeTaulaPanel("TAULA-OPTIONS");
                    //networkManager.sendLogInRequest(vista.getRequestName(), vista.getPassword());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "ORDER":
                vista.addComandaToCart(vista.getComandaToAdd(), this);
                break;
            case "DELETE":
                try {
                    System.out.println(vista.getDishToDelete());
                    networkManager.sendDishToEliminate(vista.getDishToDelete());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    public void updateMenu(ArrayList<RequestDish> menu) {
        vista.updateMenu(menu);
        vista.registerController(this);
    }

    public void updateBill(ArrayList<RequestDish> bill) {
        vista.updateBill(bill);
        vista.registerController(this);
    }

    public void correctLogin() {
        vista.changeTaulaPanel("TAULA-OPTIONS");
    }

    public void badLogin() {
        vista.badLogin();
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

            networkManager.sendRequestedDish(vista.getSelectedDish());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void resetComanda() {
        vista.clearBagOfComandes();
    }

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


}
