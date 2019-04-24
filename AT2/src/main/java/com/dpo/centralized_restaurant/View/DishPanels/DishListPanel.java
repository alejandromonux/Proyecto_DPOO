package com.dpo.centralized_restaurant.View.DishPanels;

import com.dpo.centralized_restaurant.Model.Dish;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;
import com.dpo.centralized_restaurant.View.ListButton.*;

public class DishListPanel extends JPanel {
    private JScrollPane panel;
    private JPanel dish;
    private JTable tabla;
/*
    public DishListPanel(ArrayList<Dish> dishes){
        //this.setLayout(new BorderLayout());
        String[] columnNames = {"name", "units","cost", "timecost", "historic Orders", "Delete"};
        Object[][] objects = new Object[dishes.size()][6];
        //panel = new JScrollPane();

        for(int i = 0; i < dishes.size(); i++){
            objects[i][0] = dishes.get(i).getName();
            objects[i][2] = dishes.get(i).getUnits();
            objects[i][1] = dishes.get(i).getCost();
            objects[i][3] = dishes.get(i).getTimecost();
            objects[i][4] = dishes.get(i).getHistoricOrders();
            objects[i][5] = "Delete";
        }

        tabla = new JTable(objects, columnNames);

        TableCellRenderer buttonRenderer = new ListButtonRenderer();
        tabla.getColumn("Delete").setCellRenderer(buttonRenderer);

        panel = new JScrollPane(tabla);
        this.add(panel);

    }*/

    public DishListPanel(ArrayList<Dish> dishes) {
        //this.setLayout(new BorderLayout());
        String[] columnNames = {"name", "units", "cost", "timecost", "historic Orders", "Delete"};
        Object[][] objects = new Object[dishes.size()][6];
        //panel = new JScrollPane();

        for (int i = 0; i < dishes.size(); i++) {
            objects[i][0] = dishes.get(i).getName();
            objects[i][2] = dishes.get(i).getUnits();
            objects[i][1] = dishes.get(i).getCost();
            objects[i][3] = dishes.get(i).getTimecost();
            objects[i][4] = dishes.get(i).getHistoricOrders();
            objects[i][5] = "Delete";
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(objects, columnNames);
        JTable newTabla = new JTable(model);
        newTabla.getColumn("Delete").setCellRenderer(new ListButtonRenderer());
        newTabla.getColumn("Delete").setCellEditor(new ListButtonEditor(new JCheckBox()));


        tabla = new JTable(objects, columnNames);


        TableCellRenderer buttonRenderer = new ListButtonRenderer();
        //tabla.getColumn("Delete").setCellRenderer(buttonRenderer);

        panel = new JScrollPane(newTabla);
        this.add(panel);
    }

}
