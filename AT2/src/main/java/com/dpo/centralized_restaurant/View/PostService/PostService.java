package com.dpo.centralized_restaurant.View.PostService;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Creates and handles all the information that will be displayed in the Post-Service view
 */
public class PostService extends JPanel{

    /* MAIN CONTENT ATTRIBUTES */
    private JButton jbGraphics;
    private JButton jbEndService;


    public PostService() {
        JPanel jpAuxH4 = new JPanel(new BorderLayout());
        jbGraphics = new JButton("GRAPHIC");
        jbGraphics.setForeground(Color.white);
        jbGraphics.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        ImageIcon tablesIcon = new ImageIcon("images/graph.png");
        Image image2 = tablesIcon.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(140, 140, Image.SCALE_SMOOTH); // scale it the smooth way
        tablesIcon = new ImageIcon(newimg2);
        jbGraphics.setIcon(tablesIcon);
        jbGraphics.setSize(150, 150);
        jbGraphics.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbGraphics.setHorizontalTextPosition(SwingConstants.CENTER);
        jbGraphics.setFocusable(false);
        //jbTables.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH4.add(jbGraphics, BorderLayout.CENTER);

        JPanel jpAuxH5 = new JPanel(new BorderLayout());
        jbEndService= new JButton("End Service");
        jbEndService.setForeground(Color.white);
        jbEndService.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        ImageIcon endIcon = new ImageIcon("images/off.png");
        Image imageEnd = endIcon.getImage(); // transform it
        Image newimgEnd = imageEnd.getScaledInstance(140, 200, Image.SCALE_SMOOTH); // scale it the smooth way
        endIcon = new ImageIcon(newimgEnd);
        jbEndService.setIcon(endIcon);
        jbEndService.setSize(150, 150);
        jbEndService.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbEndService.setHorizontalTextPosition(SwingConstants.CENTER);
        jbEndService.setFocusable(false);
        //jbTables.setBorder(new EmptyBorder(20, 40, 20, 40));
        jpAuxH5.add(jbEndService, BorderLayout.CENTER);

        jbGraphics.setBackground(new Color(41,48,61));
        jbEndService.setBackground(Color.black);

        this.setLayout(new GridLayout(0, 2));
        this.add(jpAuxH4);
        this.add(jpAuxH5);
    }

    public void registerControllers(Controller c){
        jbGraphics.setActionCommand("GRAPHICS");
        jbGraphics.addActionListener(c);
        jbEndService.setActionCommand("BACK-TO-MAIN");
        jbEndService.addActionListener(c);
    }
}
