package com.dpo.centralized_restaurant.View;


import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Service.Comanda;
import com.dpo.centralized_restaurant.Model.Service.ServiceDish;
import com.dpo.centralized_restaurant.View.ConfigurationPanels.ConfigurationPanel;
import com.dpo.centralized_restaurant.View.DishPanels.DishPanel;
import com.dpo.centralized_restaurant.View.PostService.PostService;
import com.dpo.centralized_restaurant.View.PostService.Stats;
import com.dpo.centralized_restaurant.View.Preservice.GeneralMenu;
import com.dpo.centralized_restaurant.View.Service.DishService;
import com.dpo.centralized_restaurant.View.Service.OrdersService;
import com.dpo.centralized_restaurant.View.TablePanels.TablePanel;
import com.dpo.centralized_restaurant.View.Service.RequestsService;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates and handles the parent panel that will handle all the views. Also it creates and displays the header
 * that will be displayed in the other views
 */
public class MainView extends JFrame {

    /* NEEDED CONTENT CLASSES */
    private GeneralMenu jpMainMenu;
    private DishPanel jpDish;
    private TablePanel jpTables;
    private ConfigurationPanel jpConfig;
    private RequestsService jpReq;
    private LogInPanel jpLogIn;
    private OrdersService jpOrders;
    private DishService jpSDish;
    private PostService jpPost;
    private Stats jpStats;

    private JPanel jpHeader;
    private JPanel jpContent;
    private CardLayout jclContent;

    /*  HEADER ATTRIBUTES */
    private JButton jbLogOut;
    private JButton jbConfig;
    private JLabel digitalClock;
    private JLabel jlWelcomeUser;

    public MainView(Controller controlador) {

        this.setLayout(new BorderLayout());
        setSize(800,450);
        this.setBackground(new Color(0x1A1A57));

        jpMainMenu = new GeneralMenu();
        jpTables = new TablePanel(controlador);
        jpDish = new DishPanel(new ArrayList<Dish>(), controlador);
        jpLogIn = new LogInPanel();
        jpReq = new RequestsService(new ArrayList<Mesa>(), controlador);
        jpOrders = new OrdersService(new ArrayList<Comanda>(), controlador);
        jpSDish = new DishService(new ArrayList<ServiceDish>(), controlador);
        //jpStats = new Stats(new ArrayList<OrderedDish>(), new ArrayList<OrderedDish>(),0, 0 , 0, (float) 0.0);
        jpPost = new PostService();
        jpConfig = new ConfigurationPanel(controlador);

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


        /*
        JPanel jpAuxH3 = new JPanel();
        jbLogOut = new JButton("LOG OUT");
        jbLogOut.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 20));
        jbLogOut.setFocusable(false);
        jbLogOut.setBorder(new EmptyBorder(10, 20, 10, 20));
        //jbLogOut.setActionCommand("LogIn");
        jpAuxH3.add(jbLogOut);*/

        JPanel miniLoginBox = new JPanel(new GridLayout(2,1));
        // Al registrase o loguearse, aquí se verá el nombre del usuario
        JPanel jpAuxH3 = new JPanel();
        jlWelcomeUser = new JLabel("Welcome, User");
        jlWelcomeUser.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 15));
        jlWelcomeUser.setForeground(Color.white);
        jlWelcomeUser.setBorder(new EmptyBorder(0, 20, 0, 20));

        jbLogOut = new JButton("LOG OUT");
        jbLogOut.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 15));
        jbLogOut.setFocusable(false);
        jbLogOut.setBorder(new EmptyBorder(2, 20, 2, 20));
        jpAuxH3.add(jlWelcomeUser);
        jpAuxH3.add(jbLogOut);
        jbLogOut.setVisible(false);
        jlWelcomeUser.setVisible(false);

        //jbLogOut.setSize(50,10);
        jpAuxH1.setBackground(null);
        jpAuxH2.setBackground(null);
        jpAuxH3.setBackground(null);
        miniLoginBox.setBackground(null);

        miniLoginBox.add(jpAuxH3);
        jbConfig = new JButton("CONFIGURATIONS");
        jbConfig.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 15));
        jbConfig.setFocusable(false);
        //jbConfig.setBorder(new EmptyBorder(2, 20, 2, 20));
        jbConfig.setVisible(false);
        miniLoginBox.add(jbConfig);

        jpHeader.add(jpAuxH1);
        jpHeader.add(jpAuxH2);
        jpHeader.add(miniLoginBox);
        jpHeader.setAlignmentY(JPanel.CENTER_ALIGNMENT);


        jpContent = new JPanel(new BorderLayout());
        jpContent.setSize(700,300);
        jpContent.setBackground(new Color(0x12123B));
        jclContent = new CardLayout();
        jpContent.setLayout(jclContent);

        jpContent.add("FORMS", jpLogIn);
        jpContent.add("MAIN", jpMainMenu);
        jpContent.add("TABLES", jpTables);
        jpContent.add("DISHES", jpDish);
        jpContent.add("REQUESTS", jpReq);
        jpContent.add("ORDERS", jpOrders);
        jpContent.add("SERVICE-DISHES", jpSDish);
        jpContent.add("POSTSERVICE", jpPost);
        jpContent.add("CONFIGURATIONS", jpConfig);
        //jpContent.add("STADISTICS", jpStats);
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
        jpReq.registerControllers(c);
        jpOrders.registerControllers(c);
        jpSDish.registerControllers(c);
        jpPost.registerControllers(c);
        jpConfig.registerController(c);

        jbLogOut.setActionCommand("FORMS");
        jbLogOut.addActionListener(c);

        jbConfig.setActionCommand("CONFIGURATIONS");
        jbConfig.addActionListener(c);
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

    public void changeConfigurationPanel (String which) { jpConfig.changePanel(which);}

    public DishPanel getJpDish() {
        return jpDish;
    }


    public TablePanel getJpTables() {
        return jpTables;
    }


    public LogInPanel getJpLogIn() {
        return jpLogIn;
    }

    public void setJpLogIn(LogInPanel jpLogIn) {
        this.jpLogIn = jpLogIn;
    }

    public RequestsService getJpReq() {
        return jpReq;
    }

    public void setJpReq(RequestsService jpReq) {
        this.jpReq = jpReq;
    }

    public void changeHeader(boolean userLogged){
        if(userLogged){
            jbLogOut.setVisible(true);
            jlWelcomeUser.setVisible(true);
            jbConfig.setVisible(true);
        }else{
            jbLogOut.setVisible(false);
            jlWelcomeUser.setVisible(false);
        }

    }
    public void changeUserView(String username){
        jlWelcomeUser.setText("Welcome " + username + "! ");
    }

    public String getConfigName() {
        return jpConfig.getConfigName();
    }

    public ConfigurationPanel getJpConfig() {
        return jpConfig;
    }
}
