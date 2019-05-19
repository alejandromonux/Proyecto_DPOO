package controller;

import model.Request;
import network.EntradaManager;
import view.Entrada.ListFrame;
import view.Entrada.ListPanel;
import view.Entrada.RequestFrame;
import view.Entrada.RequestMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Controller implements ActionListener {

    private final EntradaManager networkManager;
    private RequestFrame vista;
    private ListFrame requestList;

    public Controller(RequestFrame vista, ListFrame listFrame, EntradaManager networkManager) {
        this.networkManager = networkManager;
        this.vista = vista;
        this.requestList = listFrame;
        createClock();
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
                    //String a = ListPanel.setListener(e);
                    networkManager.deleteRequest(ListPanel.setListener(e));
                    networkManager.askRequests();
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


    public void updateRequestList(ArrayList<Request> requests, ActionListener c) {
        requestList.updateList(requests, c);
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
    public void errorConexio(){
        vista.errorConexio();
    }

    public void registerControllers(){
        vista.registerPass(this);
    }
}
