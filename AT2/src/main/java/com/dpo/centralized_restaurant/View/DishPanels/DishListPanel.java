package com.dpo.centralized_restaurant.View.DishPanels;

import com.dpo.centralized_restaurant.Model.ButtonEditor;
import com.dpo.centralized_restaurant.Model.ButtonRenderer;
import com.dpo.centralized_restaurant.Model.Dish;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import com.dpo.centralized_restaurant.View.ListButton.*;

public class DishListPanel extends JPanel {
    private JScrollPane panel;
    private JPanel dish;
    private JTable tabla;

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

        TableModel tm = new DefaultTableModel(objects, columnNames){
            public boolean isCellEditable(int row, int column) {
                if(column == columnNames.length -1) return true;
                return false;
            }
        };

        JTable newTabla = new JTable(tm);

        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < newTabla.getColumnCount();i++){
            newTabla.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        newTabla.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        newTabla.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));

        panel = new JScrollPane(newTabla);
        this.add(panel);
    }

}
