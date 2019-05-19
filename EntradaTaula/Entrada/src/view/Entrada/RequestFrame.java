package view.Entrada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestFrame extends JFrame {

    private RequestMenu jpRequestMenu;

    public RequestFrame() {

        this.setLayout(new GridLayout(1,1));
        this.setSize(800,480);

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

    public void createClock() {
        jpRequestMenu.createClock();
    }


    public void registerController (ActionListener c) {
        jpRequestMenu.registerController(c);
    }

    public void registerPass(ActionListener c){
        jpRequestMenu.registerPass(c);
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

    public void insertNotification(){

        JOptionPane.showMessageDialog(this,
                "Insert Error on creating user",
                "Inane error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void errorConexio(){
        JOptionPane.showMessageDialog(null,
                "CRestaurant may be disconnect!",
                "Connexion Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void errorComandaImposible(int id){
        JOptionPane.showMessageDialog(this,
                "La comanda " + id + " ha sido rechazada por falta de espacio en las mesas.",
                "Inane error",
                JOptionPane.ERROR_MESSAGE);
    }


}
