package com.dpo.centralized_restaurant.View.Service;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Service.ServiceDish;
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

public class DishService extends JPanel{

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

    public DishService(ArrayList<ServiceDish> dishes) {

        //renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        //editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));

        getColumNames();
        createData(dishes);
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
        jtable.getColumn("Serve").setCellRenderer(new ButtonRenderer());
        jtable.getColumn("Serve").setCellEditor(new ButtonEditor(new JCheckBox()));

        jbBack = new JButton("Back");

        this.add(jsPanel);
        this.add(jbBack);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void createData(ArrayList<ServiceDish> dishes ){
        data = new Object[dishes.size()][5];
        for (int i =0; i < dishes.size() ; i++){
            data[i][0] = dishes.get(i).getComanda().getIdTable();
            data[i][1] = dishes.get(i).getComanda().getDishname();
            data[i][2] = dishes.get(i).getComanda().getDishquant();
            data[i][3] = "Serve";
            data[i][4] = "Ready";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Table ID", "Dish name", "Quantity", "Serve"};
    }

    public void registerControllers(Controller c){
        jbBack.setActionCommand("BACKSERVICE");
        jbBack.addActionListener(c);
    }
}