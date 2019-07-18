package view.Entrada;

import controller.Controller;
import model.Request;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListFrame extends JFrame {

    private JPanel jpContainer;
    private JScrollPane jpContent;
    private ListPanel jpList;

    public ListFrame(int coordX, int coordY) {

        this.setLayout(new BorderLayout());
        this.setSize(650,500);

        jpList = new ListPanel();
        jpContent = new JScrollPane(jpList);
        jpContent.setPreferredSize(new Dimension(600,380));

        JPanel jpTitle = new JPanel(new BorderLayout());
        jpTitle.setPreferredSize(new Dimension(600,70));
        JLabel jtitle = new JLabel("Waiting List");
        jtitle.setVerticalAlignment(SwingConstants.CENTER);
        jtitle.setHorizontalAlignment(SwingConstants.CENTER);
        jtitle.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 40));
        jtitle.setForeground(Color.white);
        jpTitle.add(jtitle);

        jpContainer = new JPanel(new FlowLayout());
        jpContainer.setPreferredSize(new Dimension(600,480));
        jpContainer.add(jpTitle);
        jpContainer.add(jpContent);

        Color cAux = new Color(0x1A0D08);
        jpTitle.setBackground(cAux);
        jpContent.setBackground(cAux);
        jpContent.setBackground(cAux);

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

    /**
     * Update the list with the given requests
     * @param requests
     * @param c
     */
    public void updateList(ArrayList<Request> requests , ActionListener c) {
        //llista de requests
        //jpList.updateList(requests);
        jpList = new ListPanel(requests, c);
        //this.remove(jpContainer);
        jpContainer.remove(jpContent);
        jpContent = null;
        jpContent = new JScrollPane(jpList);
        jpContent.setPreferredSize(new Dimension(600,380));
        jpContent.setBackground(Color.white);
        jpContainer.add(jpContent);
        jpContainer.revalidate();
        jpContainer.repaint();
        this.add(jpContainer);
        revalidate();
    }

}
