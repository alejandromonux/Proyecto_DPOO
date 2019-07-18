package view.Entrada;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestMenu extends JPanel{

    private JLabel digitalClock;

    private JPanel jpPanels;
    private CardLayout cardLayout;

    private FormMenu jpFormMenu;
    private PasswordShowPanel jpPassView;

    public RequestMenu() {

        this.setLayout(new GridLayout(2,1));
        /*····················   ICON    ···················· */

        JPanel jpLogo = new JPanel(new BorderLayout());
        jpLogo.setSize(700,200);
        ImageIcon tablesIcon = new ImageIcon("images/upperLogo3.png");
        Image image2 = tablesIcon.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(400, 200,  0); // scale it the smooth way
        tablesIcon = new ImageIcon(newimg2);

        JLabel jlAux = new JLabel();
        jlAux.setSize(700,100);
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

        jpTitle.add(jtitle, BorderLayout.CENTER);
        jpTitle.add(jpAuxH2, BorderLayout.LINE_END);
        //jpTitle.setBorder(new EmptyBorder(0,0,10,0));

        /*····················   FORM    ···················· */
        jpFormMenu = new FormMenu();
        jpPassView = new PasswordShowPanel();

        cardLayout = new CardLayout();
        jpPanels = new JPanel(cardLayout);
        jpPanels.add("FORM-REQUEST", jpFormMenu);
        jpPanels.add("PASSWORD-VIEW", jpPassView);

        jpContent.add(jpTitle, BorderLayout.PAGE_START);
        jpContent.add(jpPanels, BorderLayout.CENTER);

        jpContent.setBorder(new EmptyBorder(0,40,10,40));

        /*····················   CONFIGURATIONS    ···················· */
        Color cAux = new Color(0x1A0D08);

        jpTitle.setBackground(cAux);
        jpPanels.setBackground(cAux);
        jpContent.setBackground(cAux);
        jpPassView.setBackground(cAux);
        jpFormMenu.setBackground(cAux);
        jpLogo.setBackground(cAux);
        jpAuxH2.setBackground(cAux);
        this.add(jpLogo);
        this.add(jpContent);
        this.setVisible(true);

    }

    public void changePanel(String which) {
        cardLayout.show(jpPanels, which);
    }

    /**
     * Creates the clock with the current time
     */
    public void createClock() {
        Date date = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormat.format(date);
        digitalClock.setText(time);
    }

    /**
     * Set the values that will be shown in the password view
     * @param requestName
     * @param password
     */
    public void setValuesToShow(String requestName, String password) {
        //jpPassView.showPasswordTo(requestName, password);
        jpPanels.remove(jpPassView);
        jpPassView = new PasswordShowPanel(requestName, password);
        jpPanels.add("PASSWORD-VIEW", jpPassView);
        this.repaint();
        this.revalidate();
    }

    public void resetValues() {
        jpPassView.resetValues();
    }

    public void registerController(ActionListener c) {
        jpFormMenu.registerController(c);
        jpPassView.registerController(c);
    }

    public String getRequestName() {
        return jpFormMenu.getRequestName();
    }

    public int getRequestQuantity() {
        return jpFormMenu.getRequestQuantity();
    }

    public void registerPass(ActionListener c){
        jpPassView.registerController(c);
    }

}
