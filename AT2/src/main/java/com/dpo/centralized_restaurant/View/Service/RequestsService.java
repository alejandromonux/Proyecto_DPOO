package com.dpo.centralized_restaurant.View.Service;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.View.Utils.ButtonEditor;
import com.dpo.centralized_restaurant.View.Utils.ButtonRenderer;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates and handles all the information that will be displayed in the Service requests-related view
 */
public class RequestsService extends JPanel {

    private JTable jtable;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton jbBack;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    private JScrollPane jsPanel;
    private Object[][] data ;
    private String[] columnNames;

    public RequestsService(ArrayList<Request> requests, Controller c) {

        //renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        //editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));

        getColumNames();
        createData(requests);
        TableModel tm = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                if((column == columnNames.length -1)||(column == columnNames.length -2)) return true;
                return false;
            }
        };

        jtable = new JTable(tm);
        jtable.setRowHeight(30);
        jsPanel = new JScrollPane(jtable);

        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < jtable.getColumnCount();i++){
            jtable.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        jtable.getColumn("Assign").setCellRenderer(new ButtonRenderer("Assign"));
        jtable.getColumn("Assign").setCellEditor(new ButtonEditor(new JCheckBox(), c, "ACCEPT-REQUEST", "Assign"));


        jtable.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
        jtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), c, "DECLINE-REQUEST", "Delete"));


        jbBack = new JButton("Back");
        this.add(jsPanel);
        this.add(jbBack);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void createData(ArrayList<Request> requests){
        data = new Object[requests.size()][4];
        for (int i = 0; i < requests.size() ; i++){
            data[i][0] = requests.get(i).getId();
            data[i][1] = requests.get(i).getName();
            data[i][2] = "Assign";
            data[i][3] = "Delete";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Identifier", "Name", "Assign", "Delete"};
    }



    public void registerControllers(Controller c){
        jbBack.setActionCommand("BACKSERVICE");
        jbBack.addActionListener(c);
    }

    public int getSelectedRequestName(){
        return (int) jtable.getValueAt(jtable.getSelectedRow(), 0);
    }


    public void update(ArrayList<Request> listaRequests, Controller c) {
        this.remove(jsPanel);


        getColumNames();
        createData(listaRequests);
        TableModel tm = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                if((column == columnNames.length -1)||(column == columnNames.length -2)) return true;
                return false;
            }
        };

        jtable = new JTable(tm);
        jtable.setRowHeight(30);
        jsPanel = new JScrollPane(jtable);

        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < jtable.getColumnCount();i++){
            jtable.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        jtable.getColumn("Assign").setCellRenderer(new ButtonRenderer("Assign"));
        jtable.getColumn("Assign").setCellEditor(new ButtonEditor(new JCheckBox(), c, "ACCEPT-REQUEST", "Assign"));


        jtable.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
        jtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), c, "DECLINE-REQUEST", "Delete"));


        jbBack = new JButton("Back");
        this.add(jsPanel);

        jtable.repaint();
        jsPanel.repaint();
        this.repaint();

    }
}
