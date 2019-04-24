package com.dpo.centralized_restaurant.View;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class GeneralMenu extends JPanel {

    /* MAIN CONTENT ATTRIBUTES */
    private JButton jbTables;
    private JButton jbDishes;
    private JButton jbStart;

    public GeneralMenu() {

        JPanel jpAuxH4 = new JPanel( new BorderLayout());
        jbTables = new JButton("TABLES");
        jbTables.setForeground(Color.white);
        jbTables.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        ImageIcon tablesIcon = new ImageIcon("images/tables.png");
        Image image2 = tablesIcon.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(140, 140,  Image.SCALE_SMOOTH); // scale it the smooth way
        tablesIcon = new ImageIcon(newimg2);
        jbTables.setIcon(tablesIcon);
        jbTables.setSize(150,150);
        jbTables.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbTables.setHorizontalTextPosition(SwingConstants.CENTER);
        jbTables.setFocusable(false);
        //jbTables.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH4.add(jbTables, BorderLayout.CENTER);

        JPanel jpAuxH5 = new JPanel(new BorderLayout());
        jbDishes = new JButton("DISHES");
        jbDishes.setForeground(Color.black);
        jbDishes.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        jbDishes.setSize(150,150);
        ImageIcon dishesIcon = new ImageIcon("images/dishes.jpg");
        Image image3 = dishesIcon.getImage(); // transform it
        Image newimg3 = image3.getScaledInstance(140, 140,  Image.SCALE_SMOOTH); // scale it the smooth way
        dishesIcon = new ImageIcon(newimg3);
        jbDishes.setIcon(dishesIcon);
        jbDishes.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbDishes.setHorizontalTextPosition(SwingConstants.CENTER);
        jbDishes.setFocusable(false);
        //jbDishes.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH5.add(jbDishes, BorderLayout.CENTER);


        JPanel jpAuxH6 = new JPanel(new BorderLayout());
        jbStart = new JButton("START");
        jbStart.setForeground(Color.white);
        jbStart.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        ImageIcon startIcon = new ImageIcon("images/startBtn3.jpg");
        Image image4 = startIcon.getImage(); // transform it
        Image newimg4 = image4.getScaledInstance(140, 140,  Image.SCALE_SMOOTH); // scale it the smooth way
        startIcon = new ImageIcon(newimg4);
        jbStart.setIcon(startIcon);
        jbStart.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbStart.setHorizontalTextPosition(SwingConstants.CENTER);
        jbStart.setFocusable(false);
        jbStart.setSize(150,150);
        //jbStart.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH6.add(jbStart, BorderLayout.CENTER);

        jbStart.setBackground(new Color(0x117709));
        jbDishes.setBackground(new Color(0x313236));
        jbDishes.setBackground(Color.white);
        jbTables.setBackground(new Color(0x000054));

        this.add(jpAuxH4);
        this.add(jpAuxH5);
        this.add(jpAuxH6);

        setLayout(new GridLayout(1,3));
    }

    public void registerController (Controller c) {

        jbTables.setActionCommand("TABLES");
        jbDishes.setActionCommand("DISHES");
        jbStart.setActionCommand("START");

        jbTables.addActionListener(c);
        jbDishes.addActionListener(c);
        jbStart.addActionListener(c);

    }

}
