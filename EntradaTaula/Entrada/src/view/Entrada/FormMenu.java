package view.Entrada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormMenu extends JPanel{

    private JTextField jtRequest;
    private JComboBox jcChairs;
    private JButton jbtAddRequest;

    public FormMenu() {
        /*····················   FORM    ···················· */
        JPanel jpGeneralForm = new JPanel(new GridLayout(2,1));

        JPanel jpNameForm = new JPanel(new BorderLayout());
        JLabel jRequest = new JLabel("Name of Request:");
        jRequest.setForeground(Color.white);
        jtRequest = new JTextField();
        jtRequest.setPreferredSize(new Dimension(200,20));
        jpNameForm.add(jRequest, BorderLayout.PAGE_START);
        jpNameForm.add(jtRequest, BorderLayout.CENTER);

        JPanel jpChairsForm = new JPanel(new BorderLayout());
        JLabel jChairs = new JLabel("Chairs:");

        jChairs.setForeground(Color.white);
        jcChairs = new JComboBox();
        for (int i = 0; i < 20; i++) {
            jcChairs.addItem(i+1);
        }

        jcChairs.setPreferredSize(new Dimension(200, 20));
        jpChairsForm.add(jChairs, BorderLayout.PAGE_START);
        jpChairsForm.add(jcChairs, BorderLayout.CENTER);

        jpGeneralForm.add(jpNameForm, BorderLayout.PAGE_START);
        jpGeneralForm.add(jpChairsForm, BorderLayout.CENTER);

        /*····················   ADD REQUEST    ···················· */

        JPanel jpAdd = new JPanel(new BorderLayout());
        jbtAddRequest = new JButton("MAKE REQUEST");
        jbtAddRequest.setPreferredSize(new Dimension(380,40));
        jpAdd.add(jbtAddRequest);

        Color cAux = new Color(0x1A0D08);
        jpGeneralForm.setBackground(cAux);
        jpNameForm.setBackground(cAux);
        jpChairsForm.setBackground(cAux);
        this.setBackground(cAux);

        this.setLayout(new GridLayout(2,1,0,10));
        this.add(jpGeneralForm);
        this.add(jpAdd);
    }

    public void registerController(ActionListener c) {
        jbtAddRequest.setActionCommand("REQUEST");
        jbtAddRequest.addActionListener(c);
    }

    public String getRequestName() {
        return jtRequest.getText();
    }

    public int getRequestQuantity() {
        return jcChairs.getSelectedIndex()+1;
    }

}
