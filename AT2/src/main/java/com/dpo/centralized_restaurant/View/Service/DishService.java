package com.dpo.centralized_restaurant.View.Service;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
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

/**
 * Creates and handles all the information that will be displayed in the Service dishes-related view
 */
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
    private Object[][] data ;
    private String[] columnNames;
    private JScrollPane jsPanel;

    public DishService(ArrayList<Dish> dishes, Controller c) {

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
        jsPanel = new JScrollPane(jtable);


        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < jtable.getColumnCount();i++){
            jtable.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        jtable.getColumn("Stop Serving").setCellRenderer(new ButtonRenderer("Cancel"));
        jtable.getColumn("Stop Serving").setCellEditor(new ButtonEditor(new JCheckBox(), c, "CANCEL", "Cancel"));

        jbBack = new JButton("Back");

        this.setLayout(new BorderLayout());
        this.add(jbBack, BorderLayout.PAGE_END);
        this.add(jsPanel, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void createData(ArrayList<Dish> dishes ){
        data = new Object[dishes.size()][5];
        for (int i =0; i < dishes.size() ; i++){
            data[i][0] = dishes.get(i).getName();
            data[i][1] = dishes.get(i).getCost();
            data[i][2] = dishes.get(i).getUnits();
            data[i][3] = "CANCEL";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Dish name", "Cost", "Units", "Stop Serving"};
    }

    public void registerControllers(Controller c){
        jbBack.setActionCommand("BACKSERVICE");
        jbBack.addActionListener(c);
    }

    public void update(ArrayList<Dish> dishes, Controller c){
        this.remove(jsPanel);

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
        jsPanel = new JScrollPane(jtable);


        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < jtable.getColumnCount();i++){
            jtable.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        jtable.getColumn("Stop Serving").setCellRenderer(new ButtonRenderer("Cancel"));
        jtable.getColumn("Stop Serving").setCellEditor(new ButtonEditor(new JCheckBox(), c, "CANCEL", "Cancel"));

        jbBack = new JButton("Back");

        jbBack.removeActionListener(c);
        jbBack.setActionCommand("BACKSERVICE");
        jbBack.addActionListener(c);

        this.removeAll();
        this.add(jbBack, BorderLayout.PAGE_END);
        this.add(jsPanel, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public String getDishName() {
        return jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
    }
}
