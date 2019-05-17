package controller;

import model.Request;
import network.EntradaManager;
import view.Entrada.ListFrame;
import view.Entrada.RequestFrame;
import view.Entrada.RequestMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private final EntradaManager networkManager;
    private RequestFrame vista;
    private ListFrame requestList;

    public Controller(RequestFrame vista, ListFrame listFrame, EntradaManager networkManager) {
        this.networkManager = networkManager;
        this.vista = vista;
        this.requestList = listFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            switch (e.getActionCommand()) {
                case "REQUEST":                          // En log in, l'usuari es portat a la taula
                    networkManager.sendRequest(vista.getRequestName(), vista.getRequestQuantity());
                    networkManager.askRequests();
                    break;
                case "DELETE-REQUEST":
                    networkManager.deleteRequest(vista.getRequestName());
                    break;
                case "SEEN-PASSWORD":
                    vista.changePanel("FORM-REQUEST");
                    vista.resetValues();
                    break;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void updateRequestList(ArrayList<Request> requests) {
        requestList.updateList(requests);
    }

    public void showPassword(String requestName, String passWord) {
        vista.setValuesToShow(requestName, passWord);
        vista.changePanel("PASSWORD-VIEW");
    }

    public void insertNotification(){

        vista.insertNotification();
    }
    public void notificationComanda(int id){

        vista.errorComandaImposible(id);
    }
}
