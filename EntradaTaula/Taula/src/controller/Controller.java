package controller;

import model.Dish;
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
            case "TO-TAULA-PANEL":                          // En log in, l'usuari es portat a la taula
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
            case "BACK":
                vista.changeTaulaPanel("TAULA-OPTIONS");
                break;
            case "LOG IN":
                try {
                    System.out.println(vista.getRequestName());
                    System.out.println(vista.getPassword());
                    networkManager.sendLogInRequest(vista.getRequestName(), vista.getPassword());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "ORDER":
                try {
                    System.out.println(vista.getSelectedDish());
                    networkManager.sendRequestedDish(vista.getSelectedDish());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

    public void updateMenu(ArrayList<Dish> menu) {
        vista.updateMenu(menu);
        vista.registerController(this);
    }

    public void updateBill(ArrayList<Dish> bill) {
        vista.updateBill(bill);
        vista.registerController(this);
    }

    private ArrayList<Dish> rawData() {
        ArrayList<Dish> dishes = new ArrayList<>();
        Dish dish = new Dish("Paella", 7.9,10,5);
        Dish dish1 = new Dish("Patata i bejoca", 2.9,10,3);
        Dish dish2 = new Dish("Salmo", 2.9,20,4);
        Dish dish3 = new Dish("Cafe amb llet", 5.9,15,6);
        Dish dish4 = new Dish("Rosquilles", 4.9,10,7);

        dishes.add(dish);
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish4);
        return dishes;
    }


}
