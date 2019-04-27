package com.dpo.centralized_restaurant.View.DishPanels;


import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DishesCreatorPanel extends JPanel {

    private JTextField jtfName;
    private JTextField jtCost;
    private JTextField jtTime;

    private JComboBox jcbQuantity;
    private JButton jbAdd;

    public DishesCreatorPanel() {

        JPanel jpPrimer = new JPanel(new BorderLayout());
        JLabel jlId = new JLabel("Name");
        jtfName = new JTextField();
        jtfName.setPreferredSize(new Dimension(280, 22));
        jpPrimer.add(jlId, BorderLayout.PAGE_START);
        jpPrimer.add(jtfName, BorderLayout.CENTER);

        JPanel jpSegon = new JPanel(new BorderLayout());
        JLabel jlUnits = new JLabel("Units");
        jcbQuantity = new JComboBox();

        for (int i = 0; i < 20; i++) {
            jcbQuantity.addItem(i + 1);
        }
        jcbQuantity.setPreferredSize(new Dimension(80, 22));
        jpSegon.add(jlUnits, BorderLayout.PAGE_START);
        jpSegon.add(jcbQuantity, BorderLayout.CENTER);

        JPanel jpTercer = new JPanel(new BorderLayout());
        JLabel jlCost = new JLabel("Cost");
        jtCost = new JTextField();
        jtCost.setPreferredSize(new Dimension(280, 22));
        jpTercer.add(jlCost, BorderLayout.PAGE_START);
        jpTercer.add(jtCost, BorderLayout.CENTER);

        JPanel jpCinc = new JPanel(new BorderLayout());
        JLabel jlTime = new JLabel("Time Cost");
        jtTime = new JTextField();
        jtTime.setPreferredSize(new Dimension(280, 22));
        jpCinc.add(jlTime, BorderLayout.PAGE_START);
        jpCinc.add(jtTime, BorderLayout.CENTER);

        JPanel jpQuart = new JPanel(new BorderLayout());
        jpQuart.setBorder(new EmptyBorder(0, 0, 0, 0));
        jbAdd = new JButton("ADD");
        jbAdd.setActionCommand("DISH-CREATE-ACTION");
        jpQuart.add(jbAdd);

        setLayout(new GridLayout(10, 0));
        setBorder(new EmptyBorder(70, 50, 120, 0));
        add(jpPrimer);
        add(jpSegon);
        add(jpTercer);
        add(jpCinc);
        add(jpQuart);
        setVisible(true);
    }


    public JTextField getJtfName() {
        return jtfName;
    }

    public void setJtfName(JTextField jtfName) {
        this.jtfName = jtfName;
    }

    public JTextField getJtCost() {
        return jtCost;
    }

    public void setJtCost(JTextField jtCost) {
        this.jtCost = jtCost;
    }

    public JComboBox getJcbQuantity() {
        return jcbQuantity;
    }

    public void setJcbQuantity(JComboBox jcbQuantity) {
        this.jcbQuantity = jcbQuantity;
    }

    public JTextField getJtTime() {
        return jtTime;
    }

    public void setJtTime(JTextField jtTime) {
        this.jtTime = jtTime;
    }

    public void registerController(Controller c) {
        jbAdd.addActionListener(c);
    }
}
