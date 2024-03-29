package com.dpo.centralized_restaurant.View;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.View.DishPanels.DishPanel;
import com.dpo.centralized_restaurant.View.Preservice.GeneralMenu;
import com.dpo.centralized_restaurant.View.Service.ServeiPanel;
import com.dpo.centralized_restaurant.View.TablePanels.TablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Creates and handles all the information that will be displayed in the first panel the user will see, this is the
 * Sign In and Sign Up forms. This panel might be just be displayed when no users are logged in
 */
public class LogInPanel extends JPanel {

    //--------PANELS-----------//
    private JPanel bigPanel;
    private JPanel jpForms;

    //--------BACK-----------//
    private JButton jbLogin;
    private JButton jbRegister;

    //--------MAIN-----------//

    private JButton jbSettings;

    //-------REGISTER--------//
    private JTextField jtfRegisterName;
    private JTextField jtfRegisterEmail;
    private JPasswordField jpfRegisterPassword;
    private JPasswordField jpfConfirmPassword;

    //-------LOGIN-----------//
    private JTextField jtfLoginUsername;
    private JPasswordField jpfLoginPassword;

    public LogInPanel() {

        //setLayout(new FlowLayout());
        setSize(800, 250);
        bigPanel = new JPanel(new CardLayout());
        add(bigPanel);


        //--------FORMS-----------//

        jpForms = new JPanel(new GridLayout(1,3));
        JPanel jpLeft = new JPanel();



        //Creating the Sign In form
        jpLeft.setLayout(new BoxLayout(jpLeft, BoxLayout.PAGE_AXIS));

        JLabel jlSignIn = new JLabel("Sign In");
        jlSignIn.setBorder(new EmptyBorder(20, 0, 20, 0));
        jlSignIn.setForeground(Color.black);
        //jlSignIn.setHorizontalAlignment(SwingConstants.CENTER);
        jlSignIn.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 25));
        jlSignIn.setBorder(new EmptyBorder(0,0,0,40));


        JLabel jlLoginUsername = new JLabel("Name/Email");
        jtfLoginUsername = new JTextField();
        //jtfLoginUsername.setBorder(new EmptyBorder(0,100,0,0));
        JLabel jlLoginPassword = new JLabel("Password");
        jpfLoginPassword = new JPasswordField();

        jbLogin = new JButton("Sign In");
        //jbLogin.setBorder(new EmptyBorder(0,100,30,100));
        //jbLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLogin.setHorizontalAlignment(JButton.CENTER);
        JPanel jpButton = new JPanel(new BorderLayout());
        jpButton.setBorder(new EmptyBorder(5,0,0,0));
        jbLogin = new JButton("SIGN IN");
        jpButton.add(jbLogin);

        jpLeft.add(Box.createRigidArea(new Dimension(0,20)));
        jpLeft.add(jlSignIn, BorderLayout.PAGE_START);
        jpLeft.add(Box.createRigidArea(new Dimension(0,40)));
        jpLeft.add(jlLoginUsername, BorderLayout.CENTER);
        jpLeft.add(Box.createRigidArea(new Dimension(0,10)));
        jpLeft.add(jtfLoginUsername, BorderLayout.CENTER);
        jpLeft.add(Box.createRigidArea(new Dimension(0,30)));
        jpLeft.add(jlLoginPassword, BorderLayout.CENTER);
        jpLeft.add(Box.createRigidArea(new Dimension(0,10)));
        jpLeft.add(jpfLoginPassword, BorderLayout.CENTER);
        jpLeft.add(Box.createRigidArea(new Dimension(0,60)));
        jpLeft.add(jpButton, BorderLayout.CENTER);
        jpLeft.add(Box.createRigidArea(new Dimension(0,150)));


        //CENTER TO SEPARATE

        JPanel jpCenter = new JPanel();


        //CREATING THE RIGHT PART
        JPanel jpRight = new JPanel();


        jpRight.setLayout(new BoxLayout(jpRight , BoxLayout.PAGE_AXIS));
        JLabel jlRegister = new JLabel("Register");
        jlRegister.setBorder(new EmptyBorder(20, 0, 20, 0));
        jlRegister.setForeground(Color.black);
        //jlSignIn.setHorizontalAlignment(SwingConstants.CENTER);
        jlRegister.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 25));
        jlRegister.setBorder(new EmptyBorder(0,0,0,80));

        JLabel jlRegisterName = new JLabel("Name");
        jtfRegisterName = new JTextField();
        JLabel jlRegisterEmail = new JLabel("Email");
        jtfRegisterEmail = new JTextField();
        JLabel jlRegisterPassword = new JLabel("Password");
        jpfRegisterPassword = new JPasswordField();
        JLabel jlRegisterConfirmPassword = new JLabel("Confirm Password");
        jpfConfirmPassword = new JPasswordField();

        jbRegister = new JButton();
        jbRegister.setBorder(new EmptyBorder(30,100,30,100));
        JPanel jpRegister = new JPanel(new BorderLayout());
        jpRegister.setBorder(new EmptyBorder(5,0,0,0));
        jbRegister = new JButton("SIGN UP");
        jpRegister.add(jbRegister);


        jpRight.add(Box.createRigidArea(new Dimension(0,20)));
        jpRight.add(jlRegister);
        jpRight.add(Box.createRigidArea(new Dimension(0,30)));
        jpRight.add(jlRegisterName);
        jpRight.add(Box.createRigidArea(new Dimension(0,5)));
        jpRight.add(jtfRegisterName);
        jpRight.add(Box.createRigidArea(new Dimension(0,5)));
        jpRight.add(jlRegisterEmail);
        jpRight.add(Box.createRigidArea(new Dimension(0,5)));
        jpRight.add(jtfRegisterEmail);
        jpRight.add(Box.createRigidArea(new Dimension(0,5)));
        jpRight.add(jlRegisterPassword);
        jpRight.add(Box.createRigidArea(new Dimension(0,5)));
        jpRight.add(jpfRegisterPassword);
        jpRight.add(Box.createRigidArea(new Dimension(0,5)));
        jpRight.add(jlRegisterConfirmPassword);
        jpRight.add(Box.createRigidArea(new Dimension(0,5)));
        jpRight.add(jpfConfirmPassword);
        //jbRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        jpRight.add(Box.createRigidArea(new Dimension(0,15)));
        jpRight.add(jpRegister);
        jpRight.add(Box.createRigidArea(new Dimension(0,150)));

        jpForms.add(jpLeft);
        jpForms.add(jpCenter);
        jpForms.add(jpRight);

        //--------BIG PANEL-----------//
        bigPanel.add(jpForms, "FORMS");
        add(bigPanel);
    }

    /**
     * @param c Controller de los botones
     */
    public void registerController(Controller c){
        jbLogin.setActionCommand("LOGGING USER");
        jbLogin.addActionListener(c);

        jbRegister.setActionCommand("CREATING USER");
        jbRegister.addActionListener(c);
    }

    public JTextField getJtfRegisterName() {
        return (JTextField) jtfRegisterName;
    }

    public JTextField getJtfRegisterEmail() {
        return jtfRegisterEmail;
    }

    public JPasswordField getJpfRegisterPassword() {
        return jpfRegisterPassword;
    }

    public JPasswordField getJpfConfirmPassword() {
        return jpfConfirmPassword;
    }

    public JTextField getJtfLoginUsername() {
        return jtfLoginUsername;
    }

    public JPasswordField getJpfLoginPassword() {
        return jpfLoginPassword;
    }
}
