package view.Taula;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaulaOptionsPanel extends JPanel {

    private JButton jbtnToOrder;
    private JButton jbtnSeeOrders;
    private JButton jbtnPay;

    public TaulaOptionsPanel() {

        /*····················   OPTIONS    ···················· */
        JPanel jpButtons = new JPanel(new GridLayout(1,3));

        JPanel jpAux1 = new JPanel();
        jbtnToOrder = new JButton("ORDER");
        jbtnToOrder.setPreferredSize(new Dimension(120,150));
        jpAux1.add(jbtnToOrder);
        jpAux1.setBorder(new EmptyBorder(50,0,0,0));

        JPanel jpAux2 = new JPanel();
        jbtnSeeOrders = new JButton("SEE ORDERS");
        jbtnSeeOrders.setPreferredSize(new Dimension(120,150));
        jpAux2.add(jbtnSeeOrders);
        jpAux2.setBorder(new EmptyBorder(50,0,0,0));


        JPanel jpAux3 = new JPanel();
        jbtnPay = new JButton("PAY");
        jbtnPay.setPreferredSize(new Dimension(120,150));
        jpAux3.add(jbtnPay);
        jpAux3.setBorder(new EmptyBorder(50,0,0,0));

        jpButtons.add(jpAux1);
        jpButtons.add(jpAux2);
        jpButtons.add(jpAux3);

        Color cAux = new Color(0x1A0D08);
        jpAux1.setBackground(cAux);
        jpAux2.setBackground(cAux);
        jpAux3.setBackground(cAux);

        this.setLayout(new BorderLayout());
        this.add(jpButtons);

    }

    public void registerController(Controller c) {
        jbtnToOrder.setActionCommand("TAULA-ORDER");
        jbtnToOrder.addActionListener(c);

        jbtnSeeOrders.setActionCommand("TAULA-SEE-ORDERS");
        jbtnSeeOrders.addActionListener(c);

        jbtnPay.setActionCommand("TO-PAY");
        jbtnPay.addActionListener(c);
    }
}
