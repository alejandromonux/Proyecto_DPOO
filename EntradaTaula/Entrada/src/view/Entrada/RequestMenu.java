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

public class RequestMenu extends JPanel {

    private JTextField jtRequest;
    private JComboBox jcChairs;
    private JButton jbtAddRequest;
    private JLabel digitalClock;

    public RequestMenu() {

        this.setLayout(new BorderLayout());
        this.setSize(400,480);
        this.setBackground(new Color(0x914225));

        JPanel jpLogo = new JPanel(new BorderLayout());
        jpLogo.setSize(400,200);
        ImageIcon tablesIcon = new ImageIcon("images/upperLogo3.png");
        Image image2 = tablesIcon.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(400, 200,  0); // scale it the smooth way
        tablesIcon = new ImageIcon(newimg2);

        JLabel jlAux = new JLabel();
        jlAux.setSize(400,100);
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
        jpTitle.setBorder(new EmptyBorder(0,0,10,0));

        /*····················   FORM    ···················· */

        JPanel jpGeneralForm = new JPanel(new GridLayout(3,1));

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

        jpContent.add(jpTitle, BorderLayout.PAGE_START);
        jpContent.add(jpGeneralForm, BorderLayout.CENTER);

        jpContent.setBorder(new EmptyBorder(10,40,10,40));

        /*····················   ADD REQUEST    ···················· */

        JPanel jpAdd = new JPanel(new BorderLayout());
        jbtAddRequest = new JButton("MAKE REQUEST");
        jbtAddRequest.setPreferredSize(new Dimension(400,40));
        jpAdd.add(jbtAddRequest);


        /*····················   CONFIGURATIONS    ···················· */
        //Color cAux = new Color(0x472316);
        Color cAux = new Color(0x1A0D08);

        jpTitle.setBackground(cAux);
        jpGeneralForm.setBackground(cAux);
        jpContent.setBackground(cAux);
        jpNameForm.setBackground(cAux);
        jpChairsForm.setBackground(cAux);
        jpLogo.setBackground(cAux);
        jpAuxH2.setBackground(cAux);


        this.add(jpLogo, BorderLayout.PAGE_START);
        this.add(jpContent, BorderLayout.CENTER);
        this.add(jpAdd, BorderLayout.PAGE_END);
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

        jbtAddRequest.setActionCommand("REQUEST");
        jbtAddRequest.addActionListener(c);

    }

    public String getRequestName() {
        return jtRequest.getText();
    }

    public int getRequestQuantity() {
        return jcChairs.getSelectedIndex();
    }
}
