package view.Entrada;

import controller.Controller;
import model.Request;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListPanel extends JPanel {

    private ArrayList<Request> content;
    private JButton jbtnDelete;
    private static ArrayList<JButton> botones = new ArrayList<>();

    public ListPanel() {

        content = new ArrayList<>();
        jbtnDelete = new JButton("DELETE");
        generalLlista();

        //this.setBorder(new EmptyBorder(10,10,0,10));
    }

    public ListPanel(ArrayList<Request> list, ActionListener c){
        content = list;
        generaLlista(list);
        for (int i = 0; i < botones.size(); i++){
            if (botones.get(i).getActionListeners().length > 0) {
                botones.get(i).removeActionListener(c);
            }
            botones.get(i).removeActionListener(c);
            botones.get(i).addActionListener(c);
            botones.get(i).setActionCommand("DELETE-REQUEST");
        }
        //this.setBorder(new EmptyBorder(10,10,0,10));
    }

    public void registerController(Controller c) {
        if (jbtnDelete.getActionListeners().length > 0) {
            jbtnDelete.removeActionListener(c);
        }
        jbtnDelete.setActionCommand("DELETE-REQUEST");
        jbtnDelete.addActionListener(c);
        generalLlista();
    }

    private void generalLlista() {
        this.setLayout(new GridLayout(content.size(),1,0,10));
        updateList(new ArrayList<Request>());
    }
    private void generaLlista(ArrayList<Request> list){
        this.setLayout(new GridLayout(content.size(), 1, 0, 10));
        updateList(list);
    }

    public void updateList(ArrayList<Request> list) {
        this.removeAll();
        Color cAux = new Color(0x1A0D08);

        content = (ArrayList<Request>) list.clone();
        for (int i = 0; i < content.size();i++) {
            JPanel jpAux = new JPanel(new GridLayout(1,3,5,0));
            JLabel jlAux;
            JLabel jlPass = new JLabel();

            if (content.get(i).getMesa_name() == "---"){
                jlPass.setText("Esperando asignacion de mesa...");
                jlPass.setForeground(Color.white);
                jlAux = new JLabel(content.get(i).getName());
                 jlAux.setForeground(Color.white);
            }else {
                if (list.get(i).getPassword() == "---") {
                    jlPass.setText("En cola");
                    jlPass.setForeground(Color.white);
                    jlAux = new JLabel(content.get(i).getName() );
                    jlAux.setForeground(Color.white);
                } else {
                    jlPass.setText( "Password:   " + content.get(i).getPassword());
                    jlPass.setForeground(Color.white);
                    jlAux = new JLabel(content.get(i).getName());
                    jlAux.setForeground(Color.white);
                }
            }
            //jlAux.setForeground(Color.white);

            jpAux.add(jlAux);
            jpAux.add(jlPass);

            if (list.get(i).getPassword() == "---") {
                JButton jbtnAux = new JButton("DELETE " + content.get(i).getName());
                botones.add(jbtnAux);
                jbtnAux.setActionCommand("DELETE-REQUEST");
                jbtnAux.setPreferredSize(new Dimension(150,20));
                jpAux.add(jbtnAux);
            } else {
                jpAux.add(new JLabel(""));
            }

            jpAux.setPreferredSize(new Dimension(450,30));
            jpAux.setBorder(new EmptyBorder(0,20,0,20));
            jpAux.setBackground(cAux);
            this.add(jpAux);

        }
        repaint();
    }

    public static String setListener(ActionEvent e){
        JButton aux = new JButton(" ");
        for (int i = 0; botones.size() > i; i++){
            if (botones.get(i) == e.getSource()){
                aux = botones.get(i);
                break;
            }
        }
        return aux.getText().substring(7, aux.getText().length());
    }
}
