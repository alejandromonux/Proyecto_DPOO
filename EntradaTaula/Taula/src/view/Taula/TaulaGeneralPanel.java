package view.Taula;

import controller.Controller;
import model.Dish;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaulaGeneralPanel extends JPanel {

    private JLabel digitalClock;

    private CardLayout cardLayout;
    private JPanel jpPanels;

    private TaulaLoginPanel userLogin;
    private TaulaOptionsPanel userOptions;
    private ToOrderPanel orderPanel;
    private SeeOrdersPanel requestedOrders;
    private PaymentPanel paymentPanel;


    public TaulaGeneralPanel() {

        this.setLayout(new BorderLayout());
        this.setSize(800,380);

        JPanel jpLogo = new JPanel(new BorderLayout());
        jpLogo.setSize(300,380);
        ImageIcon tablesIcon = new ImageIcon("images/sideLogo.png");
        Image image2 = tablesIcon.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(320, 380,  0); // scale it the smooth way
        tablesIcon = new ImageIcon(newimg2);

        JLabel jlAux = new JLabel();
        jlAux.setSize(350,380);
        jlAux.setHorizontalAlignment(0);
        jlAux.setIcon(tablesIcon);

        jpLogo.add(jlAux);

        /*····················   CONTENT    ···················· */

        JPanel jpContent = new JPanel(new BorderLayout());
        /*····················   TITLE    ···················· */

        JPanel jpTitle = new JPanel(new BorderLayout());
        JLabel jtitle = new JLabel("CRestaurant");
        jtitle.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 40));
        jtitle.setForeground(Color.white);

        JPanel jpAuxH2 = new JPanel(new BorderLayout());
        digitalClock = new JLabel();
        digitalClock.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 30));
        digitalClock.setForeground(Color.white);
        digitalClock.setHorizontalAlignment(JLabel.CENTER);
        createClock();
        jpAuxH2.add(digitalClock);


        jpTitle.add(jtitle, BorderLayout.LINE_START);
        jpTitle.add(jpAuxH2, BorderLayout.CENTER);
        jpTitle.setBorder(new EmptyBorder(14,0,0,0));

        /*····················   CONTENT    ···················· */
        userLogin = new TaulaLoginPanel();
        userOptions = new TaulaOptionsPanel();
        orderPanel = new ToOrderPanel();
        requestedOrders = new SeeOrdersPanel();
        paymentPanel = new PaymentPanel();

        cardLayout = new CardLayout();
        jpPanels = new JPanel(cardLayout);
        jpPanels.add("TAULA-LOGIN", userLogin);
        jpPanels.add("TAULA-OPTIONS", userOptions);
        jpPanels.add("TAULA-ORDER", orderPanel);
        jpPanels.add("TAULA-SEE-ORDERS", requestedOrders);
        jpPanels.add("TAULA-PAY", paymentPanel);


        /*····················   CONFIGURATION    ···················· */

        jpContent.add(jpTitle, BorderLayout.PAGE_START);
        jpContent.add(jpPanels, BorderLayout.CENTER);


        Color cAux = new Color(0x1A0D08);
        jpLogo.setBackground(cAux);
        jpContent.setBackground(cAux);
        jpTitle.setBackground(cAux);
        jpAuxH2.setBackground(cAux);


        this.add(jpLogo, BorderLayout.LINE_START);
        this.add(jpContent, BorderLayout.CENTER);
        this.setVisible(true);

    }

    public void createClock() {
        Timer timer;
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String time = timeFormat.format(date);
                digitalClock.setText(time);
            }
        };
        timer = new Timer(1000, actionListener);
        timer.setInitialDelay(0);
        timer.start();
    }

    public void registerController(Controller c) {
        userLogin.registerController(c);
        userOptions.registerController(c);
        orderPanel.registerController(c);
        requestedOrders.registerController(c);
        paymentPanel.registerController(c);
    }

    public void changePanel(String which) {
        cardLayout.show(jpPanels, which);
    }

    public String getRequestName() {
        return userLogin.getJtRequest().getText();
    }

    public String getPassword() {
        return userLogin.getJtPassword().getText();
    }

    public String getSelectedDish() {
        return orderPanel.getSelectedDish();
    }

    public String getDishToDelete() {
        return requestedOrders.getDishToDelete();
    }

    public void updateMenu(ArrayList<Dish> menu) {
        orderPanel.updateMenu(menu);
    }

    public void updateBill(ArrayList<Dish> bill) {
        requestedOrders.updateDishes(bill);
    }
}
