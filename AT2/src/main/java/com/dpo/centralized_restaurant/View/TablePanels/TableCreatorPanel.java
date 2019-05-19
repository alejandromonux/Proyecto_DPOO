package com.dpo.centralized_restaurant.View.TablePanels;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates the left side of the panel that the user will see in the view, this being a group of actions
 * the user will be allowed to do
 */
public class TableCreatorPanel extends JPanel{

    private JTextField jtfId;
    private JComboBox jcbQuantity;
    private JButton jbAdd;

    /**
     * Panel de creación de tablas, con la parte de creación y la lista
     */
    public TableCreatorPanel() {


        JPanel jpPrimer = new JPanel(new BorderLayout());
        JLabel jlId = new JLabel("Identifier");
        jtfId = new JTextField();
        jtfId.setPreferredSize(new Dimension(280, 22));
        jpPrimer.add(jlId, BorderLayout.PAGE_START);
        jpPrimer.add(jtfId, BorderLayout.CENTER);


        JPanel jpSegon = new JPanel(new BorderLayout());
        JLabel jlChairs = new JLabel("Chairs");
        jcbQuantity = new JComboBox();

        for (int i = 0; i < 20; i++) {
            jcbQuantity.addItem(i+1);
        }

        jcbQuantity.setPreferredSize(new Dimension(80, 22));
        jpSegon.add(jlChairs, BorderLayout.PAGE_START);
        jpSegon.add(jcbQuantity, BorderLayout.CENTER);


        JPanel jpTercer = new JPanel(new BorderLayout());
        jpTercer.setBorder(new EmptyBorder(5,0,0,0));
        jbAdd = new JButton("ADD TABLE");
        jbAdd.setActionCommand("TABLE-CREATE-ACTION");
        //jpTercer.add(jbAdd);

        setLayout(new GridLayout(10,0));
        setBorder(new EmptyBorder(80,30,120,0));
        add(jpPrimer);
        add(jpSegon);
        add(jbAdd);
        setVisible(true);
    }

    public void registerController(Controller c) {
        jbAdd.addActionListener(c);
    }

    public void refreshCreatorPanel() {

    }

    public JTextField getJtfId() {
        return jtfId;
    }

    public void setJtfId(JTextField jtfId) {
        this.jtfId = jtfId;
    }

    public JComboBox getJcbQuantity() {
        return jcbQuantity;
    }

    public void setJcbQuantity(JComboBox jcbQuantity) {
        this.jcbQuantity = jcbQuantity;
    }
}
