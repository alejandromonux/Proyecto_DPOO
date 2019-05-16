package com.dpo.centralized_restaurant.View.Preservice;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Creates and handles all the information that will be displayed in the Pre-Service view
 */
public class ChoicesPanel extends JPanel {

    private JButton jbTables;
    private JButton jbDishes;
    private JButton jbStart;


    public ChoicesPanel() {

        this.setLayout(new GridLayout(1,3,50,0));

        jbTables = new JButton("TABLES");
        ImageIcon tablesIcon = new ImageIcon("images/tables.png");
        Image image2 = tablesIcon.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(140, 140,  Image.SCALE_SMOOTH); // scale it the smooth way
        tablesIcon = new ImageIcon(newimg2);
        jbTables.setIcon(tablesIcon);
        jbTables.setSize(150,150);
        jbTables.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbTables.setHorizontalTextPosition(SwingConstants.CENTER);
        jbTables.setBorder(new EmptyBorder(20, 40, 20, 40));


        jbDishes = new JButton("DISHES");
        jbDishes.setSize(150,150);
        ImageIcon dishesIcon = new ImageIcon("images/dishes.jpg");
        Image image3 = dishesIcon.getImage(); // transform it
        Image newimg3 = image3.getScaledInstance(140, 140,  Image.SCALE_SMOOTH); // scale it the smooth way
        dishesIcon = new ImageIcon(newimg3);
        jbDishes.setIcon(dishesIcon);
        jbDishes.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbDishes.setHorizontalTextPosition(SwingConstants.CENTER);
        jbDishes.setBorder(new EmptyBorder(20, 40, 20, 40));


        jbStart = new JButton("START");
        ImageIcon startIcon = new ImageIcon("images/startBtn3.jpg");
        Image image4 = startIcon.getImage(); // transform it
        Image newimg4 = image4.getScaledInstance(140, 140,  Image.SCALE_SMOOTH); // scale it the smooth way
        startIcon = new ImageIcon(newimg4);
        jbStart.setIcon(startIcon);
        jbStart.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbStart.setHorizontalTextPosition(SwingConstants.CENTER);
        jbStart.setSize(150,150);
        jbStart.setBorder(new EmptyBorder(20, 40, 20, 40));

        this.add(jbTables);
        this.add(jbDishes );
        this.add(jbStart);
        this.setBackground(new Color(0x191A21));
    }

    public void registerController() {

    }

}
