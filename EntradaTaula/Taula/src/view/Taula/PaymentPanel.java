package view.Taula;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaymentPanel extends JPanel {

    private double bill;

    private JButton jbtnBack;
    private JButton jbtnPay;

    public PaymentPanel() {

        this.setLayout(new GridLayout(2,1,0,10));

        JPanel jpBill = new JPanel(new GridLayout(2,1,0,10));
        JLabel jlPref = new JLabel("TOTAL COST OF SERVICE: ");
        jlPref.setForeground(Color.white);
        jlPref.setHorizontalAlignment(JLabel.CENTER);
        jlPref.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 25));
        JLabel jlBill = new JLabel(Double.toString(bill) + "$");
        jlBill.setForeground(Color.white);
        jlBill.setHorizontalAlignment(JLabel.CENTER);
        jlBill.setFont(new Font("Chaparral Pro Light", Font.BOLD, 40));
        jpBill.add(jlPref);
        jpBill.add(jlBill);
        jpBill.setBorder(new EmptyBorder(50,0,0,0));

        JPanel jpButtons = new JPanel(new FlowLayout());
        jbtnPay = new JButton("PAY");
        jbtnPay.setPreferredSize(new Dimension(180,40));
        jbtnBack = new JButton("BACK");
        jbtnBack.setPreferredSize(new Dimension(180,40));
        jpButtons.add(jbtnBack);
        jpButtons.add(jbtnPay);

        Color cAux = new Color(0x1A0D08);
        this.setBackground(cAux);
        jpBill.setBackground(cAux);
        jpButtons.setBackground(cAux);

        this.setBorder(new EmptyBorder(0,10,0,10));
        this.add(jpBill, BorderLayout.PAGE_END);
        this.add(jpButtons);

    }

    public void registerController(ActionListener c) {
        jbtnBack.setActionCommand("BACK");
        jbtnBack.addActionListener(c);
        jbtnPay.setActionCommand("PAY-BILL");
        jbtnPay.addActionListener(c);
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}
