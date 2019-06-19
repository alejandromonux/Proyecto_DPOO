package view.Taula;

import model.RequestDish;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PaymentPanel extends JPanel {

    private final static Color cAux = new Color(0x1A0D08);

    private double bill;

    private JButton jbtnBack;
    private JButton jbtnPay;
    private JPanel jpButtons;
    private JPanel jpBill;

    public PaymentPanel() {

        this.setLayout(new GridLayout(2,1,0,10));

        jpBill = new JPanel(new GridLayout(2,1,0,10));
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

        jpButtons = new JPanel(new FlowLayout());
        jbtnPay = new JButton("PAY");
        jbtnPay.setPreferredSize(new Dimension(180,40));
        jbtnBack = new JButton("BACK");
        jbtnBack.setPreferredSize(new Dimension(180,40));
        jpButtons.add(jbtnBack);
        jpButtons.add(jbtnPay);

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
        if (jbtnPay.getActionListeners().length > 0) {
            jbtnPay.removeActionListener(c); }
        jbtnPay.setActionCommand("PAY-BILL");
        jbtnPay.addActionListener(c);
    }

    public void updateBill(ArrayList<RequestDish> comandes) {
        float result = 0;
        for (RequestDish rd: comandes) {
            result += rd.getCost();
        }
        bill = result;
    }

    public void updateView() {
        jpBill = new JPanel(new GridLayout(2,1,0,10));
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
        jpBill.setBackground(cAux);

        this.removeAll();
        this.add(jpBill, BorderLayout.PAGE_END);
        this.add(jpButtons);
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}
