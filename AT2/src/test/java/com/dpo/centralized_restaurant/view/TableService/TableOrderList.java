package com.dpo.centralized_restaurant.view.TableService;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.View.ListButton.ListButtonEditor;
import com.dpo.centralized_restaurant.View.ListButton.ListButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;

public class TableOrderList extends JPanel {
    JScrollPane panel;
    JButton back;

    public TableOrderList(ArrayList<Mesa> mesas){
        String[] columnNames = {"Id reserva", "Persones" ,"Assignar", "Rebutjar"};
        Object[][] objects = new Object[mesas.size()][4];

        for (int i = 0; i < mesas.size(); i++) {
            objects[i][0] = mesas.get(i).getId();
            objects[i][1] = mesas.get(i).getChairs();
            objects[i][2] = "Assignar";
            objects[i][3] = "Rebutjar";
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(objects, columnNames);
        JTable newTabla = new JTable(model);
        newTabla.getColumn("Assignar").setCellRenderer(new ListButtonRenderer());
        newTabla.getColumn("Rebutjar").setCellEditor(new ListButtonEditor(new JCheckBox()));


        //tabla = new JTable(objects, columnNames);


        TableCellRenderer buttonRenderer = new ListButtonRenderer();
        //tabla.getColumn("Delete").setCellRenderer(buttonRenderer);

        panel = new JScrollPane(newTabla);
        this.add(panel);

        back = new JButton("Back");
        back.setActionCommand("BACKSERVICE");
        this.add(back);
    }

    public void registerControllers(Controller c){

        back.addActionListener(c);
    }

}
