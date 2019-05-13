package view.Entrada;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ListFrame extends JFrame {

    private JPanel jpContainer;
    private JScrollPane jpContent;
    private ListPanel jpList;

    public ListFrame(int coordX, int coordY) {

        this.setLayout(new BorderLayout());
        this.setSize(400,480);

        jpList = new ListPanel();
        jpContent = new JScrollPane(jpList);
        jpContent.setPreferredSize(new Dimension(400,380));
        jpContent.setBorder(new EmptyBorder(10,0,30,0));

        JPanel jpTitle = new JPanel(new BorderLayout());
        jpTitle.setPreferredSize(new Dimension(400,80));
        JLabel jtitle = new JLabel("Waiting List");
        jtitle.setVerticalAlignment(SwingConstants.CENTER);
        jtitle.setHorizontalAlignment(SwingConstants.CENTER);
        jtitle.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 40));
        jtitle.setForeground(Color.white);
        jpTitle.add(jtitle);

        jpContainer = new JPanel(new FlowLayout());
        jpContainer.setPreferredSize(new Dimension(400,480));
        jpContainer.add(jpTitle);
        jpContainer.add(jpContent);

        Color cAux = new Color(0x1A0D08);
        jpTitle.setBackground(cAux);
        jpContent.setBackground(Color.white);
        //jpContent.setBackground(new Color(0xCB7E2E));

        jpContainer.setBackground(cAux);

        this.add(jpContainer);
        this.setLocation( coordX , coordY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("ENTRADA-LIST");
        this.setResizable(false);
    }

    public void registerController(Controller c) {
        jpList.registerController(c);
    }

    public void updateList(ArrayList<String> requests) {
        jpList.updateList(requests);
    }

}
