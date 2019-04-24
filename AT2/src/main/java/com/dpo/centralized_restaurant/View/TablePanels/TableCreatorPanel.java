package com.dpo.centralized_restaurant.View.TablePanels;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TableCreatorPanel extends JPanel{

    private JTextField jtfId;
    private JComboBox jcbQuantity;
    private JButton jbAdd;

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
        jbAdd = new JButton("ADD");
        jpTercer.add(jbAdd);

        setLayout(new BorderLayout(0,10));
        setBorder(new EmptyBorder(0,70,120,0));
        add(jpPrimer, BorderLayout.PAGE_START);
        add(jpSegon, BorderLayout.CENTER);
        add(jpTercer, BorderLayout.PAGE_END);
        setVisible(true);
    }

    public void registerController(Controller c) {
        jbAdd.setActionCommand("TABLE-CREATE");
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
