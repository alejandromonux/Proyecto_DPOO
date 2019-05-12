package controller;

import model.Dish;
import network.EntradaManager;
import network.NetworkManager;
import view.Entrada.ListFrame;
import view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private final EntradaManager networkManager;
    private MainWindow vista;
    private ListFrame requestList;

    public Controller(MainWindow vista, ListFrame listFrame, EntradaManager networkManager) {
        this.networkManager = networkManager;
        this.vista = vista;
        this.requestList = listFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            switch (e.getActionCommand()) {
                case "REQUEST":                          // En log in, l'usuari es portat a la taula
                    vista.changePanel("REQUEST");
                    networkManager.sendRequest(vista.getRequestName(), vista.getRequestQuantity());
                    requestList.addRequest();
                    break;
                case "DELETE-REQUEST":
                    networkManager.deleteRequest(vista.getRequestName());
                    break;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void updateRequestList(ArrayList<String> requests) {

    }

}
