package com.dpo.centralized_restaurant.View.DishPanels;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.View.Utils.ButtonEditor;
import com.dpo.centralized_restaurant.View.Utils.ButtonRenderer;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

/**
 * Creates the right size of panel that the user will see in the view, this being a list of the dishes
 */
public class DishListPanel extends JPanel {
    private JScrollPane panel;
    private JPanel dish;
    private JTable tabla;
    private ButtonEditor buttonEditor;

    /**
     *
     * @param dishes lista de platos a poner en la tabla
     * @param c Controller par alos botones de la tabla
     */
    public DishListPanel(ArrayList<Dish> dishes, Controller c) {
        buttonEditor = new ButtonEditor(new JCheckBox(), c, "REMOVE-DISH", "Delete");
        //this.setLayout(new BorderLayout());
        String[] columnNames = {"name", "cost", "units", "timecost", "historic Orders", "Delete"};
        Object[][] objects = new Object[dishes.size()][6];
        //panel = new JScrollPane();

        for (int i = 0; i < dishes.size(); i++) {
            objects[i][0] = dishes.get(i).getName();
            objects[i][1] = dishes.get(i).getCost();
            objects[i][2] = dishes.get(i).getUnits();
            objects[i][3] = dishes.get(i).getTimecost();
            objects[i][4] = dishes.get(i).getHistoricOrders();
            objects[i][5] = "Delete";
        }

        TableModel tm = new DefaultTableModel(objects, columnNames){
            public boolean isCellEditable(int row, int column) {
                if(column == columnNames.length -1) return true;
                return false;
            }
        };

        //JTable newTabla = new JTable(tm);
        tabla = new JTable(tm);
        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < tabla.getColumnCount();i++){
            tabla.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        tabla.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
        tabla.getColumn("Delete").setCellEditor(buttonEditor);

        panel = new JScrollPane(tabla);
        this.add(panel);
    }

    public String getDishName() {
        System.out.println(tabla.getSelectedRow());
        return tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
    }

    /**
     *
     * @param dishes lsit de platos de la nueva tabla
     * @param c Controller de la nueva tabla
     */
    public void update(ArrayList<Dish> dishes, Controller c){
        this.remove(panel);

        buttonEditor = new ButtonEditor(new JCheckBox(), c, "REMOVE-DISH", "Delete");
        //this.setLayout(new BorderLayout());
        String[] columnNames = {"name", "cost", "units", "timecost", "historic Orders", "Delete"};
        Object[][] objects = new Object[dishes.size()][6];
        //panel = new JScrollPane();

        for (int i = 0; i < dishes.size(); i++) {
            objects[i][0] = dishes.get(i).getName();
            objects[i][1] = dishes.get(i).getCost();
            objects[i][2] = dishes.get(i).getUnits();
            objects[i][3] = dishes.get(i).getTimecost();
            objects[i][4] = dishes.get(i).getHistoricOrders();
            objects[i][5] = "Delete";
        }

        TableModel tm = new DefaultTableModel(objects, columnNames){
            public boolean isCellEditable(int row, int column) {
                if(column == columnNames.length -1) return true;
                return false;
            }
        };

        tabla = new JTable(tm);
        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < tabla.getColumnCount();i++){
            tabla.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        tabla.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
        tabla.getColumn("Delete").setCellEditor(buttonEditor);

        panel = new JScrollPane(tabla);
        this.add(panel);
    }
}
