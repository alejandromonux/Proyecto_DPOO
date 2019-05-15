package com.dpo.centralized_restaurant.View.ConfigurationPanels;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Creates the left side of the panel that the user will see in the view, this being a group of actions
 * the user will be allowed to do
 */
public class ConfigurationCreatorPanel extends JPanel{

    private JTextField jtfId;
    private JButton jbAdd;

    public ConfigurationCreatorPanel() {


        JPanel jpPrimer = new JPanel(new BorderLayout());
        JLabel jlId = new JLabel("Identifier");
        jtfId = new JTextField();
        jtfId.setPreferredSize(new Dimension(280, 22));
        jpPrimer.add(jlId, BorderLayout.PAGE_START);
        jpPrimer.add(jtfId, BorderLayout.CENTER);

        JPanel jpTercer = new JPanel(new BorderLayout());
        jpTercer.setBorder(new EmptyBorder(5,0,0,0));
        jbAdd = new JButton("SAVE-CONFIGURATION");
        jbAdd.setActionCommand("CONFIGURATION-CREATE-ACTION");
        //jpTercer.add(jbAdd);

        setLayout(new GridLayout(10,0,0,10));
        setBorder(new EmptyBorder(80,30,120,0));
        add(jpPrimer);
        add(jbAdd);
        setVisible(true);
    }

    public void registerController(Controller c) {
        jbAdd.addActionListener(c);
    }

    public JTextField getJtfId() {
        return jtfId;
    }

    public void setJtfId(JTextField jtfId) {
        this.jtfId = jtfId;
    }

}
