package com.dpo.centralized_restaurant.view.TablePanels;

import com.dpo.centralized_restaurant.controller.Controller;
import com.dpo.centralized_restaurant.model.Table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TablePanel extends JPanel{


    private ArrayList<Table> tables = new ArrayList<>();
    private JButton jbCreate;
    private JButton jbList;
    private JButton jbBack;

    private JPanel jpContent;
    private JScrollPane jpList;
    private CardLayout jclContent;


    private TableCreatorPanel jpCreator;
    private TablesListPanel tableList;

    public TablePanel() {

        Table t1 = new Table(1,4);
        Table t2 = new Table(2,8);
        Table t3 = new Table(3,12);
        Table t4 = new Table(4,5);
        tables.add(t1);
        tables.add(t2);
        tables.add(t3);
        tables.add(t4);

        JPanel jpLeft = new JPanel(new BorderLayout(0,15));
        jpLeft.setBackground(null);

        JPanel jpBigLeft = new JPanel(new BorderLayout());
        jpBigLeft.setBorder(new EmptyBorder(0, 50, 90, 50));
        jpBigLeft.setSize(800,250);

        JLabel title = new JLabel("TABLES");
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

        jpContent = new JPanel(new BorderLayout());
        jpContent.setSize(500,200);
        jpContent.setBorder(new EmptyBorder(0, 300, 110, 250));

        jpBigLeft.setBackground(new Color(0x232375));
        jpBigLeft.setBackground(new Color(0x03091C));

        tableList = new TablesListPanel();
        jpCreator = new TableCreatorPanel();
        jclContent = new CardLayout();
        jpContent.setLayout(jclContent);
        jpContent.add("TABLE-CREATE", jpCreator);
        jpContent.add("TABLE-LIST", tableList);

        setLayout(new SpringLayout());
        setSize(800,250);
        add(jpBigLeft);
        add(jpContent);
    }

    public void registerController(Controller c) {

        jbCreate.setActionCommand("TABLE-CREATE");
        jbList.setActionCommand("TABLE-LIST");
        jbBack.setActionCommand("TABLE-BACK");
        jbCreate.addActionListener(c);
        jbList.addActionListener(c);
        jbBack.addActionListener(c);
    }

    public TablePanel getPanel(TablePanel which) {return which;}

    public void changePanel(String which) {
        jclContent.show(jpContent, which);
    }

}
