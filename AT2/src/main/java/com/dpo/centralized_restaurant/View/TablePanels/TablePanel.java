package com.dpo.centralized_restaurant.View.TablePanels;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class TablePanel extends JPanel{

    private JButton jbCreate;
    private JButton jbList;
    private JButton jbBack;

    private JPanel jpContent;
    private CardLayout jclContent;

    private TableCreatorPanel jpCreator;
    private TablesListPanel jpList;

    public TablePanel() {

        JPanel jpLeft = new JPanel(new BorderLayout(0,15));
        jpLeft.setBackground(null);

        JPanel jpBigLeft = new JPanel(new BorderLayout());
        jpBigLeft.setBorder(new EmptyBorder(0, 50, 90, 50));
        jpBigLeft.setSize(800,250);

        JLabel title = new JLabel("TABLES");
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 25));


        JPanel jpAuxH1 = new JPanel(new BorderLayout());
        jbCreate = new JButton("CREATE");
        jbCreate.setBackground(Color.white);
        jbCreate.setFocusable(false);
        jpAuxH1.add(jbCreate);
        jbCreate.setBorder(new EmptyBorder(10, 70, 10, 70));


        JPanel jpAuxH2 = new JPanel(new BorderLayout());
        jbList = new JButton("LIST");
        jbList.setBackground(Color.white);
        jbList.setFocusable(false);
        jbList.setBorder(new EmptyBorder(10, 70, 10, 70));
        jpAuxH2.add(jbList);
        //jpAuxH2.setBorder(new EmptyBorder(40, 0, 40, 0));


        JPanel jpAuxH3 = new JPanel(new BorderLayout());
        jbBack = new JButton("BACK");
        jbBack.setBorder(new EmptyBorder(20, 40, 20, 40));
        jbBack.setBackground(Color.white);
        jbBack.setFocusable(false);
        jpAuxH3.add(jbBack);
        jpAuxH3.setBorder(new EmptyBorder(70, 0, 70, 0));
        jpAuxH3.setBackground(null);


        jpLeft.add(jpAuxH1, BorderLayout.PAGE_START);
        jpLeft.add(jpAuxH2, BorderLayout.CENTER);
        jpLeft.add(jpAuxH3, BorderLayout.PAGE_END);

        jpBigLeft.add(title, BorderLayout.PAGE_START);
        jpBigLeft.add(jpLeft, BorderLayout.CENTER);

        jpContent = new JPanel(new BorderLayout());
        jpContent.setSize(500,200);
        jpContent.setBorder(new EmptyBorder(0, 0, 0, 0));

        jpBigLeft.setBackground(new Color(0x232375));
        jpBigLeft.setBackground(new Color(0x03091C));

        //CREATE JTABLE LIST INFO, IS HARDCODED TILL WE CAN GET IT FROM THE DATABASE

        String[] columnNames = {"Identifier", "Chairs", ""};
        Object[][] data =
                {
                        {"Table 1", "4", "delete table"},
                        {"Table 2", "3", "delete table"},
                        {"Table 3",  "6", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        /*{"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},
                        {"Table 666",  "8", "delete table"},*/
                        {"Table 5",  "8", "delete table"},


                };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable( model );
        table.setVisible(false);
        table.setRowHeight(30);
        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < table.getColumnCount();i++){
                table.getColumnModel().getColumn(i).setCellRenderer(df);
        }

        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                ((DefaultTableModel)table.getModel()).removeRow(modelRow);
            }
        };

        jpList = new TablesListPanel(table, delete, 2);
        jpList.setMnemonic(KeyEvent.VK_D);
        jpCreator = new TableCreatorPanel();
        jclContent = new CardLayout();
        jpContent.setBorder(new EmptyBorder(100, 300, 0, 250));
        jpContent.setLayout(jclContent);
        jpContent.add("TABLE-CREATE", jpCreator);
        jpContent.add("TABLE-LIST", table);

       // setLayout(new FlowLayout());
        setLayout(new SpringLayout());
        setSize(800,250);
        add(jpBigLeft);
        add(jpContent);
    }

    public void registerController(Controller c) {

        jbCreate.setActionCommand("TABLE-CREATE");
        jbList.setActionCommand("TABLE-LIST");
        jbBack.setActionCommand("TABLE-BACK");
        jbCreate.addActionListener(c);
        jbList.addActionListener(c);
        jbBack.addActionListener(c);
        jpCreator.registerController(c);
    }

    public TablePanel getPanel(TablePanel which) {return which;}

    public void changePanel(String which) {
        jclContent.show(jpContent, which);
    }

    public TableCreatorPanel getJpCreator() {
        return jpCreator;
    }

    public void setJpCreator(TableCreatorPanel jpCreator) {
        this.jpCreator = jpCreator;
    }

    public TablesListPanel getJpList() {
        return jpList;
    }

    public void setJpList(TablesListPanel jpList) {
        jpContent.remove(1);
        this.jpList = jpList;
     //   jpContent.add("TABLE-LIST", table);
    }
}
