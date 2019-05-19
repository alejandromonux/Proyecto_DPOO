package com.dpo.centralized_restaurant.View.Service;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Service.Comanda;
import com.dpo.centralized_restaurant.View.Utils.ButtonEditor;
import com.dpo.centralized_restaurant.View.Utils.ButtonRenderer;

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
 * Creates and handles all the information that will be displayed in the Service orders-related view
 */
public class OrdersService extends JPanel{

    private JTable jtable;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton jbBack;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    private Object[][] data ;
    private String[] columnNames;
    private com.dpo.centralized_restaurant.View.ListButton.ButtonEditor buttonEditor;
    private JScrollPane jsPanel;

    public OrdersService(ArrayList<Comanda> comandas, Controller c) {
        buttonEditor = new com.dpo.centralized_restaurant.View.ListButton.ButtonEditor(new JCheckBox());
        buttonEditor.setTextButton("SEE MORE");
        editButton = new JButton();
        editButton.setFocusPainted(false);

        getColumNames();
        createData(comandas);
        TableModel tm = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                if(column == columnNames.length -1) return true;
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
        jtable.getColumn("").setCellRenderer(new ButtonRenderer("SEE"));
        jtable.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), c, "SEE-TABLE-ORDERS", "SEE"));
        ButtonEditor nbe = new ButtonEditor(new JCheckBox(), c, "SEE-TABLE-ORDERS", "SEE");
        jtable.getColumn("").setCellEditor(nbe);
        jbBack = new JButton("BACK");

        this.setLayout(new BorderLayout());
        this.add(jbBack, BorderLayout.PAGE_END);
        this.add(jsPanel, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void createData(ArrayList<Comanda> comandas ){
        data = new Object[comandas.size()][6];
        for (int i =0; i < comandas.size() ; i++){
            data[i][0] = comandas.get(i).getIdTable();
            data[i][1] = comandas.get(i).getAllDishes();
            data[i][2] = comandas.get(i).getPendingDishes();
            data[i][3] = comandas.get(i).getCookingDishes();
            data[i][4] = comandas.get(i).getDate();
            data[i][5] = "SEE MORE";
        }
    }


    public void getColumNames(){
        columnNames = new String[]{"Mesa ID", "All Dishes", "Pending Dishes", "Cooking dishes", "Time", ""};
    }

    public void registerController(Controller c){
        jbBack.setActionCommand("BACKSERVICE");
        jbBack.addActionListener(c);
    }

    public int getOrderID() {
        return (int)jtable.getValueAt(jtable.getSelectedRow(), 0);
    }

    public void update(ArrayList<Comanda> comandas, Controller c){
        this.remove(jsPanel);
        buttonEditor = new com.dpo.centralized_restaurant.View.ListButton.ButtonEditor(new JCheckBox());
        buttonEditor.setTextButton("SEE MORE");
        editButton = new JButton();
        editButton.setFocusPainted(false);

        getColumNames();
        createData(comandas);
        TableModel tm = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                if(column == columnNames.length -1) return true;
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
        jtable.getColumn("").setCellRenderer(new ButtonRenderer("SEE"));
        jtable.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), c, "SEE-TABLE-ORDERS", "SEE"));
        ButtonEditor nbe = new ButtonEditor(new JCheckBox(), c, "SEE-TABLE-ORDERS", "SEE");
        jtable.getColumn("").setCellEditor(nbe);
        jbBack = new JButton("BACK");

        this.setLayout(new BorderLayout());
        this.add(jbBack, BorderLayout.PAGE_END);
        this.add(jsPanel, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }
}
