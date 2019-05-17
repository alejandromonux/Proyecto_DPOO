package view.Entrada;

import controller.Controller;
import model.Request;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ListPanel extends JPanel {

    private ArrayList<Request> content;
    private JButton jbtnDelete;

    public ListPanel() {

        content = new ArrayList<>();
        jbtnDelete = new JButton("DELETE");
        //generaStrings();
        generalLlista();

        this.setBorder(new EmptyBorder(10,10,0,10));
    }

    public void registerController(Controller c) {
        jbtnDelete.setActionCommand("DELETE-REQUEST");
        jbtnDelete.addActionListener(c);
        generalLlista();
    }

   /* private void generaStrings() {
        content.add(new String("LE ROUMAND"));
        content.add(new String("LE ROCAFORTE"));
        content.add(new String("SVRISHNAIM"));
        content.add(new String("FREIGHTZ"));
        content.add(new String("OHAM"));
        content.add(new String("SACRATE QUAIR"));
        content.add(new String("PHOENIX"));
        content.add(new String("49FURINVRVJNRV"));
        content.add(new String("RGVTRVT"));
        content.add(new String("R4GRVTRBYB"));
        content.add(new String("PTHY6THOENIX"));
        content.add(new String("Joan"));

    }*/

    private void generalLlista() {
        this.setLayout(new GridLayout(content.size(),1,0,10));
        updateList(new ArrayList<>());
    }

    public void updateList(ArrayList<Request> list) {

        Color cAux = new Color(0x1A0D08);

        content = (ArrayList<Request>) list.clone();
        for (int i = 0; i < content.size();i++) {
            JPanel jpAux = new JPanel(new GridLayout(1,2,0,10));
            JLabel jlAux;
            if (content.get(i).getMesa_name() == null){
                 jlAux = new JLabel(content.get(i).getName() + "\t Esperando asignacion de mesa...");
            }else {
                if (list.get(i).getPassword() == null) {
                    jlAux = new JLabel(content.get(i).getName() + "\t En cola");
                } else {
                    jlAux = new JLabel(content.get(i).getName() + "\t Pass: " + content.get(i).getPassword());
                }
            }
            jlAux.setForeground(Color.white);

            JButton jbtnAux = new JButton("DELETE");
            jbtnAux.setActionCommand("DELETE-REQUEST");
            jbtnAux.setPreferredSize(new Dimension(50,20));
            jbtnAux.setBackground(new Color(0x791311));
            jbtnAux.setForeground(Color.white);

            //jbtnAux.addActionListener(jbtnDelete.getActionListeners()[0]);
            jpAux.add(jlAux);
            jpAux.add(jbtnAux);
            jpAux.setPreferredSize(new Dimension(350,30));
            jpAux.setBorder(new EmptyBorder(0,20,0,20));
            jpAux.setBackground(cAux);
            this.add(jpAux);
        }
    }
}
