package com.dpo.centralized_restaurant.View.Service;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Preservice.Table;
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
    Object[][] data ;
    String[] columnNames;

    public OrdersService(ArrayList<Comanda> comandas) {

        //renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        //editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));

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
        JScrollPane jsPanel = new JScrollPane(jtable);


        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < jtable.getColumnCount();i++){
            jtable.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        jtable.getColumn("Prepare").setCellRenderer(new ButtonRenderer());
        jtable.getColumn("Prepare").setCellEditor(new ButtonEditor(new JCheckBox()));

        jbBack = new JButton("Back");

        this.add(jsPanel);
        this.add(jbBack);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void createData(ArrayList<Comanda> comandas ){
        data = new Object[comandas.size()][4];
        for (int i =0; i < comandas.size() ; i++){
            data[i][0] = comandas.get(i).getIdTable();
            data[i][1] = comandas.get(i).getDishname();
            data[i][2] = comandas.get(i).getDishquant();
            data[i][3] = "Prepare";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Table ID", "Dish name", "Quantity", "Prepare"};
    }

    public void registerControllers(Controller c){
        jbBack.setActionCommand("BACKSERVICE");
        jbBack.addActionListener(c);
    }
}
