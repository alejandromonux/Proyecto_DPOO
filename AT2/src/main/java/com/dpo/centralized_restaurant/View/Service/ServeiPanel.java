package com.dpo.centralized_restaurant.View.Service;

import com.dpo.centralized_restaurant.Controller.Controller;


import javax.swing.*;
import java.awt.*;

/**
 * Creates and handles all the information that will be displayed in the general Service view
 */
public class ServeiPanel extends JPanel {

    /* MAIN CONTENT ATTRIBUTES */
    private JButton jbOrders;
    private JButton jbRequests;
    private JButton jbDishes;
    private JButton jbStop;


    public ServeiPanel() {


        JPanel jpAuxH4 = new JPanel(new BorderLayout());
        jbOrders = new JButton("TABLE ORDERS");
        jbOrders.setForeground(Color.white);
        jbOrders.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        ImageIcon tablesIcon = new ImageIcon("images/dishes.jpg");
        Image image2 = tablesIcon.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(140, 140, Image.SCALE_SMOOTH); // scale it the smooth way
        tablesIcon = new ImageIcon(newimg2);
        jbOrders.setIcon(tablesIcon);
        jbOrders.setSize(150, 150);
        jbOrders.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbOrders.setHorizontalTextPosition(SwingConstants.CENTER);
        jbOrders.setFocusable(false);
        //jbTables.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH4.add(jbOrders, BorderLayout.CENTER);

        JPanel jpAuxH5 = new JPanel(new BorderLayout());
        jbRequests = new JButton("REQUESTS");
        jbRequests.setForeground(Color.white);
        jbRequests.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        jbRequests.setSize(150, 150);
        ImageIcon dishesIcon = new ImageIcon("images/tables.png");
        Image image3 = dishesIcon.getImage(); // transform it
        Image newimg3 = image3.getScaledInstance(140, 140, Image.SCALE_SMOOTH); // scale it the smooth way
        dishesIcon = new ImageIcon(newimg3);
        jbRequests.setIcon(dishesIcon);
        jbRequests.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbRequests.setHorizontalTextPosition(SwingConstants.CENTER);
        jbRequests.setFocusable(false);
        //jbDishes.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH5.add(jbRequests, BorderLayout.CENTER);


        JPanel jpAuxH6 = new JPanel(new BorderLayout());
        jbDishes = new JButton("DISHES");
        jbDishes.setForeground(Color.white);
        jbDishes.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        ImageIcon startIcon = new ImageIcon("images/dishes.png");
        Image image4 = startIcon.getImage(); // transform it
        Image newimg4 = image4.getScaledInstance(140, 140, Image.SCALE_SMOOTH); // scale it the smooth way
        startIcon = new ImageIcon(newimg4);
        jbDishes.setIcon(startIcon);
        jbDishes.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbDishes.setHorizontalTextPosition(SwingConstants.CENTER);
        jbDishes.setFocusable(false);
        jbDishes.setSize(150, 150);
        //jbStart.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH6.add(jbDishes, BorderLayout.CENTER);


        JPanel jpAuxH7 = new JPanel(new BorderLayout());
        jbStop = new JButton("STOP SERVICE");
        jbStop.setForeground(Color.white);
        jbStop.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        ImageIcon stopIcon = new ImageIcon("images/startBtn2.jpg");
        Image image5 = stopIcon.getImage(); // transform it
        Image newimg5 = image5.getScaledInstance(140, 140, Image.SCALE_SMOOTH); // scale it the smooth way
        stopIcon = new ImageIcon(newimg5);
        jbStop.setIcon(stopIcon);
        jbStop.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbStop.setHorizontalTextPosition(SwingConstants.CENTER);
        jbStop.setFocusable(false);
        jbStop.setSize(150, 150);
        //jbStart.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH7.add(jbStop, BorderLayout.CENTER);

        jbDishes.setBackground(new Color(0x757577));
        jbRequests.setBackground(new Color(0x721425));
        jbOrders.setBackground(new Color(0xC55006));
        jbStop.setBackground(Color.RED);

        this.add(jpAuxH4);
        this.add(jpAuxH5);
        this.add(jpAuxH6);
        this.add(jpAuxH7);
        setLayout(new GridLayout(1, 4));
    }


    public void registerController(Controller c) {

        jbOrders.setActionCommand("TABLE-ORDERS");
        jbRequests.setActionCommand("REQUESTS");
        jbDishes.setActionCommand("SERVICE-DISHES");
        jbStop.setActionCommand("POSTSERVICE");

        jbOrders.addActionListener(c);
        jbRequests.addActionListener(c);
        jbDishes.addActionListener(c);
        jbStop.addActionListener(c);
    }

}
