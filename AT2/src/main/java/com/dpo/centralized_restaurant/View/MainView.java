package com.dpo.centralized_restaurant.View;


import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Table;
import com.dpo.centralized_restaurant.View.DishPanels.DishPanel;
import com.dpo.centralized_restaurant.View.TablePanels.TablePanel;
import com.dpo.centralized_restaurant.View.TableService.TableOrderList;
import com.dpo.centralized_restaurant.Model.Dish;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainView extends JFrame {

    /* NEEDED CONTENT CLASSES */
    private GeneralMenu jpMainMenu;
    private DishPanel jpDish;
    private TablePanel jpTables;
    private ServeiPanel jpStart;
    private LogInPanel jpLogIn;
    private TableOrderList jpServiceTables;

    private JPanel jpHeader;
    private JPanel jpContent;
    private CardLayout jclContent;

    /*  HEADER ATTRIBUTES */
    private JButton jbLogOut;
    private JLabel digitalClock;

    public MainView() {

        this.setLayout(new BorderLayout());
        setSize(800,450);
        this.setBackground(new Color(0x1A1A57));

        jpMainMenu = new GeneralMenu();
        jpTables = new TablePanel();
        jpDish = new DishPanel(new ArrayList<Dish>());
        jpStart = new ServeiPanel(new ArrayList<Table>());
        jpLogIn = new LogInPanel();
        jpServiceTables = new TableOrderList(new ArrayList<Table>());

        jpHeader = new JPanel();
        jpHeader.setSize(700,100);
        jpHeader.setLayout(new GridLayout(1,3));
        jpHeader.setBorder(new EmptyBorder(10, 0, 10, 0));
        jpHeader.setBackground(new Color(0x03091C));


        JPanel jpAuxH1 = new JPanel(new BorderLayout());
        JLabel title = new JLabel("CRestaurant");
        title.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 40));

        title.setForeground(Color.white);
        title.setHorizontalAlignment(JLabel.CENTER);
        jpAuxH1.add(title, BorderLayout.CENTER);

        JPanel jpAuxH2 = new JPanel(new BorderLayout());
        digitalClock = new JLabel();
        digitalClock.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 30));
        digitalClock.setForeground(Color.white);
        digitalClock.setHorizontalAlignment(JLabel.CENTER);
        createClock();
        jpAuxH2.add(digitalClock, BorderLayout.CENTER);

        JPanel jpAuxH3 = new JPanel();
        jbLogOut = new JButton("LOG OUT");
        jbLogOut.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        jbLogOut.setFocusable(false);
        jbLogOut.setBorder(new EmptyBorder(10, 20, 10, 20));
        //jbLogOut.setActionCommand("LogIn");
        jpAuxH3.add(jbLogOut);

        //jbLogOut.setSize(50,10);
        jpAuxH1.setBackground(null);
        jpAuxH2.setBackground(null);
        jpAuxH3.setBackground(null);

        jpHeader.add(jpAuxH1);
        jpHeader.add(jpAuxH2);
        jpHeader.add(jpAuxH3);
        jpHeader.setAlignmentY(JPanel.CENTER_ALIGNMENT);

        jpContent = new JPanel(new BorderLayout());
        jpContent.setSize(700,300);
        jpContent.setBackground(new Color(0x12123B));
        jclContent = new CardLayout();
        jpContent.setLayout(jclContent);
        jpContent.add("MAIN", jpMainMenu);
        jpContent.add("TABLES", jpTables);
        jpContent.add("DISHES", jpDish);
        jpContent.add("START", jpStart);
        jpContent.add("FORMS", jpLogIn);
//        jpContent.add("TABLE-ORDERS", jpServiceTables);
        /* ------------------------------ VIEW PARAMETERS ------------------------------ */
        getContentPane().add(jpHeader, BorderLayout.PAGE_START);
        getContentPane().add(jpContent);
       // getContentPane().add(jpContent, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pre-Service");
        setResizable(false);

    }

    public void registerController(Controller c) {
        jpMainMenu.registerController(c);
        jpTables.registerController(c);
        jpDish.registerController(c);
        jpLogIn.registerController(c);
        jpStart.registerController(c);

        jbLogOut.setActionCommand("FORMS");
        jbLogOut.addActionListener(c);
    }

    public void createClock() {
        Timer timer;
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String time = timeFormat.format(date);
                digitalClock.setText(time);
            }
        };
        timer = new Timer(1000, actionListener);
        timer.setInitialDelay(0);
        timer.start();
    }

    public void changePanel (String which) {

        jclContent.show(jpContent,which);
    }

    public void changeTablePanel (String which) {
        jpTables.changePanel(which);
    }

    public TablePanel getTablePanel() {return jpTables;}

    public DishPanel getJpDish() {
        return jpDish;
    }

    public void setJpDish(DishPanel jpDish) {
        this.jpDish = jpDish;
    }

    public TablePanel getJpTables() {
        return jpTables;
    }

    public void setJpTables(TablePanel jpTables) {
        this.jpTables = jpTables;
    }

    public ServeiPanel getJpStart() {
        return jpStart;
    }

    public void setJpStart(ServeiPanel jpStart) {
        this.jpStart = jpStart;
    }

    public LogInPanel getJpLogIn() {
        return jpLogIn;
    }

    public void setJpLogIn(LogInPanel jpLogIn) {
        this.jpLogIn = jpLogIn;
    }
}
