package com.dpo.centralized_restaurant.View;


import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Service.Comanda;
import com.dpo.centralized_restaurant.Model.Service.ServiceDish;
import com.dpo.centralized_restaurant.View.ConfigurationPanels.ConfigurationPanel;
import com.dpo.centralized_restaurant.View.DishPanels.DishPanel;
import com.dpo.centralized_restaurant.View.PostService.PostService;
import com.dpo.centralized_restaurant.View.PostService.Stats;
import com.dpo.centralized_restaurant.View.Preservice.GeneralMenu;
import com.dpo.centralized_restaurant.View.Service.*;
import com.dpo.centralized_restaurant.View.TablePanels.TablePanel;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.View.TablePanels.TablesListPanel;

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
    private DeepOrderPanel jpTableOrders;
    private DishService jpSDish;
    private PostService jpPost;
    private ServeiPanel jpServiceHome;
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
        setSize(800,500);
        this.setBackground(new Color(0x1A1A57));

        jpServiceHome = new ServeiPanel();
        jpMainMenu = new GeneralMenu();
        jpTables = new TablePanel(controlador);
        jpDish = new DishPanel(new ArrayList<Dish>(), controlador);
        jpLogIn = new LogInPanel();
        jpReq = new RequestsService(new ArrayList<Request>(), controlador);
        jpOrders = new OrdersService(new ArrayList<Comanda>(), controlador);
        jpSDish = new DishService(new ArrayList<Dish>(), controlador);
        jpTableOrders = new DeepOrderPanel(new ArrayList<>(),controlador, "");
        jpStats = new Stats(new ArrayList<OrderedDish>(), new ArrayList<OrderedDish>(),0, 0 , 0, (float) 0.0);
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

        JPanel miniLoginBox = new JPanel(new GridLayout(3,0));
        // Al registrase o loguearse, aquí se verá el nombre del usuario
        JPanel jpAuxH3 = new JPanel();
        jlWelcomeUser = new JLabel("Welcome, User");
        jlWelcomeUser.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 15));
        jlWelcomeUser.setForeground(Color.white);
        jlWelcomeUser.setBorder(new EmptyBorder(0, 20, 0, 20));

        jbLogOut = new JButton("LOG OUT");
        jbLogOut.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 10));
        jbLogOut.setFocusable(false);
        //jbLogOut.setBorder(new EmptyBorder(2, 20, 2, 20));
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

        //miniLoginBox.add(jpAuxH3);
        //miniLoginBox.add(jlWelcomeUser);
        //miniLoginBox.add(jbLogOut);
        jbConfig = new JButton("CONFIGURATIONS");
        jbConfig.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 15));
        jbConfig.setFocusable(false);
        //jbConfig.setBorder(new EmptyBorder(2, 20, 2, 20));
        jbConfig.setVisible(false);
        miniLoginBox.add(jbConfig);
        //jpAuxH3.add(jbConfig);

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
        jpContent.add("SPECIFIC-ORDERS", jpTableOrders);
        jpContent.add("SERVICE-DISHES", jpSDish);
        jpContent.add("CONFIGURATIONS", jpConfig);
        jpContent.add("START", jpServiceHome);
        jpContent.add("POSTSERVICE", jpPost);
        jpContent.add("STADISTICS", jpStats);
        /* ------------------------------ VIEW PARAMETERS ------------------------------ */
        getContentPane().add(jpHeader, BorderLayout.PAGE_START);
        getContentPane().add(jpContent);
        // getContentPane().add(jpContent, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");
        setResizable(false);

    }

    public void registerController(Controller c) {
        jpMainMenu.registerController(c);
        jpTables.registerController(c);
        jpDish.registerController(c);
        jpLogIn.registerController(c);
        jpReq.registerControllers(c);
        jpOrders.registerController(c);
        jpSDish.registerControllers(c);
        jpPost.registerControllers(c);
        jpConfig.registerController(c);
        jpServiceHome.registerController(c);
        jpTableOrders.registerController(c);
        jpStats.registerController(c);
        jbLogOut.setActionCommand("FORMS");
        jbLogOut.addActionListener(c);

        jbConfig.setActionCommand("CONFIGURATIONS");
        jbConfig.addActionListener(c);
    }

    /**
     * Gets the current time in hours:minutes:seconds
     */
    public void createClock() {
        Date date = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormat.format(date);
        digitalClock.setText(time);

    }

    /**Cambia el panel del cardPanel
     *
     * @param which String que indica a cuál cambiar
     */
    public void changePanel (String which) {
        jclContent.show(jpContent,which);
    }

    public boolean isSpecificPanel() {
        JPanel card = null;
        for (Component comp : jpContent.getComponents()) {
            if (comp.isVisible()) {
                card = (JPanel) comp;
            }
        }
        return card.equals("SPECIFIC-ORDERS");
    }

    /**Cambia el panel
     *
     * @param which String que indica a cuál cambiar
     */
    public void changeTablePanel (String which) {
        jpTables.changePanel(which);
    }

    /**Se cambia el panel de configuración
     *
     * @param which String que indica a qué panel pasar
     */
    public void changeConfigurationPanel (String which) { jpConfig.changePanel(which);}


    public void setTableDishOrder(ArrayList<Comanda> comandes, Controller c) {
        jpContent.remove(jpOrders);
        this.jpOrders = new OrdersService(comandes, c);
        this.jpOrders.registerController(c);
        jpContent.add("ORDERS", jpOrders);
        jclContent.show(jpContent, "ORDERS");
    }

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

    public Stats getJpStats() {
        return jpStats;
    }

    public void setJpStats(Stats jpStats) {
        jpContent.remove(this.jpStats);
        this.jpStats = jpStats;
        jpContent.add("STADISTICS", this.jpStats);
    }

    public DishService getJpSDish() {
        return jpSDish;
    }

    public void setJpSDish(DishService jpSDish) {
        jpContent.remove(this.jpSDish);
        this.jpSDish = jpSDish;
        jpContent.add("SERVICE-DISHES", jpSDish);
    }

    public OrdersService getJpOrders() {
        return jpOrders;
    }

    public void setJpOrders(OrdersService jpOrders) {
        this.jpOrders = jpOrders;
    }

    public DeepOrderPanel getJpTableOrders() {
        return jpTableOrders;
    }

    public void setJpTableOrders(DeepOrderPanel jpTableOrders) {
        jpContent.remove(this.jpTableOrders);
        this.jpTableOrders = jpTableOrders;
        jpContent.add("SPECIFIC-ORDERS", jpTableOrders);
    }

    public void updateRequests(ArrayList<Request> listaRequests, Controller controller){
        jpReq.update(listaRequests, controller);
    }

    public String getConfigName() {
        return jpConfig.getConfigName();
    }

    public ConfigurationPanel getJpConfig() {
        return jpConfig;
    }

    public void hideConfiguration(){
        jbConfig.setVisible(false);
    }

    public void showConfiguration(){
        jbConfig.setVisible(true);
    }
}