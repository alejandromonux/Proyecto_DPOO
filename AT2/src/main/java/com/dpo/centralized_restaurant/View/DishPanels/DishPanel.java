package com.dpo.centralized_restaurant.View.DishPanels;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class DishPanel extends JPanel {

    private DishesCreatorPanel jpCreator;
    private DishListPanel jpList;

    private JButton jbCreate;
    private JButton jbList;
    private JButton jbBack;

    private JPanel jpContent;
    private CardLayout jclContent;


    public DishPanel(ArrayList<Dish> dishes) {
        jpCreator = new DishesCreatorPanel();
        jpList = new DishListPanel(dishes);

        JPanel jpLeft = new JPanel(new BorderLayout(0,15));
        jpLeft.setBackground(null);

        JPanel jpBigLeft = new JPanel(new BorderLayout());
        jpBigLeft.setBorder(new EmptyBorder(0, 50, 90, 50));
        jpBigLeft.setSize(800,250);

        JLabel title = new JLabel("DISHES");
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 25));


        JPanel jpAuxH1 = new JPanel(new BorderLayout());
        jbCreate = new JButton("CREATE");
        jbCreate.setBackground(Color.white);
        jbCreate.setFocusable(false);
        jpAuxH1.add(jbCreate);
        jbCreate.setBorder(new EmptyBorder(10, 70, 10, 70));


        JPanel jpAuxH2 = new JPanel(new BorderLayout());
        jbList = new JButton("LIST");
        jbList.setBackground(Color.white);
        jbList.setFocusable(false);
        jbList.setBorder(new EmptyBorder(10, 70, 10, 70));
        jpAuxH2.add(jbList);
        //jpAuxH2.setBorder(new EmptyBorder(40, 0, 40, 0));


        JPanel jpAuxH3 = new JPanel(new BorderLayout());
        jbBack = new JButton("BACK");
        jbBack.setBorder(new EmptyBorder(20, 40, 20, 40));
        jbBack.setBackground(Color.white);
        jbBack.setFocusable(false);
        jpAuxH3.add(jbBack);
        jpAuxH3.setBorder(new EmptyBorder(70, 0, 70, 0));
        jpAuxH3.setBackground(null);


        jpLeft.add(jpAuxH1, BorderLayout.PAGE_START);
        jpLeft.add(jpAuxH2, BorderLayout.CENTER);
        jpLeft.add(jpAuxH3, BorderLayout.PAGE_END);

        jpBigLeft.add(title, BorderLayout.PAGE_START);
        jpBigLeft.add(jpLeft, BorderLayout.CENTER);

        jclContent = new CardLayout();
        jpContent = new JPanel();
        jpContent.setBorder(new EmptyBorder(0, 300, 0, 250));
        jpContent.setLayout(jclContent);

        jpList.setSize(new Dimension((int)(this.getSize().getWidth() - jpBigLeft.getSize().width), this.getHeight()-100));
        jpContent.add("DISH-CREATE", jpCreator);
        jpContent.add("DISH-LIST", jpList);


        jpBigLeft.setBackground(new Color(0x974807));
        setLayout(new SpringLayout());
        setSize(800,250);
        add(jpBigLeft);
        add(jpContent);
    }
    public void registerController (Controller c) {

        jbCreate.setActionCommand("DISH-CREATE");
        jbList.setActionCommand("DISH-LIST");
        jbBack.setActionCommand("DISH-BACK");
        jbCreate.addActionListener(c);
        jbList.addActionListener(c);
        jbBack.addActionListener(c);
        jpCreator.registerController(c);
    }


    public DishesCreatorPanel getJpCreator() {
        return jpCreator;
    }

    public void setJpCreator(DishesCreatorPanel jpCreator) {
        this.jpCreator = jpCreator;
    }

    public DishListPanel getJpList() {
        return jpList;
    }

    public void setJpList(DishListPanel jpList) {
        jpContent.remove(1);
        this.jpList = jpList;
        jpContent.add("DISH-LIST", this.jpList);

    }

    public void changePanel (String which) {
        jclContent.show(jpContent,which);
    }

    public JPanel getJpContent() {
        return jpContent;
    }

    public void setJpContent(JPanel jpContent) {
        this.jpContent = jpContent;
    }
}
