package view.Taula;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaulaLoginPanel extends JPanel {

    private JTextField jtRequest;
    private JPasswordField jtPassword;

    private JButton jbtnLogIn;

    public TaulaLoginPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0x1A0D08));

        /*····················   FORM    ···················· */
        JPanel jpGeneralForm = new JPanel(new FlowLayout());

        JPanel jpRequestPart = new JPanel(new BorderLayout(0,10));
        JLabel jRequest = new JLabel("Login:");

        jRequest.setForeground(Color.white);
        jtRequest = new JTextField();
        jtRequest.setPreferredSize(new Dimension(350,30));
        jpRequestPart.add(jRequest, BorderLayout.PAGE_START);
        jpRequestPart.add(jtRequest, BorderLayout.CENTER);


        JPanel jpPasswordPart = new JPanel(new BorderLayout(0,10));
        JLabel jlPassword = new JLabel("Password:");

        jlPassword.setForeground(Color.white);
        jtPassword = new JPasswordField();
        jtPassword.setPreferredSize(new Dimension(350,30));
        jpPasswordPart.add(jlPassword, BorderLayout.PAGE_START);
        jpPasswordPart.add(jtPassword, BorderLayout.CENTER);

        JPanel jpButton = new JPanel(new BorderLayout());
        jbtnLogIn = new JButton("LOG IN");
        jbtnLogIn.setForeground(Color.white);
        jbtnLogIn.setBackground(new Color(0xCB7E2E));
        jbtnLogIn.setPreferredSize(new Dimension(350,50));
        jpButton.add(jbtnLogIn);
        jpButton.setBorder(new EmptyBorder(20,0,0,0));


        jpGeneralForm.add(jpRequestPart);
        jpGeneralForm.add(jpPasswordPart);
        jpGeneralForm.add(jpButton);

        Color cAux = new Color(0x1A0D08);

        jpButton.setBackground(cAux);
        jpRequestPart.setBackground(cAux);
        jpPasswordPart.setBackground(cAux);
        jpGeneralForm.setBackground(cAux);

        jpGeneralForm.setBorder(new EmptyBorder(40,40,10,40));

        this.add(jpGeneralForm);
        this.setVisible(true);
    }

    public void registerController(Controller c) {
        if (jbtnLogIn.getActionListeners().length > 0) {
            jbtnLogIn.removeActionListener(c); }
        jbtnLogIn.setActionCommand("LOG IN");
        jbtnLogIn.addActionListener(c);
    }

    public JTextField getJtRequest() {
        return jtRequest;
    }

    public JTextField getJtPassword() {
        return jtPassword;
    }
}
