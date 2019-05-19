package com.dpo.centralized_restaurant.View.ConfigurationPanels;

//import sun.tools.jps.Jps;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
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
 * Creates the right size of panel that the user will see in the view, this being a list of the configurations
 */

public class ConfigurationListPanel extends JPanel{

    private JTable jtable;

    private JButton editButton;
    Object[][] data ;
    String[] columnNames;
    private com.dpo.centralized_restaurant.View.ListButton.ButtonEditor buttonEditor;

    /**
     *
     * @param configs Lista de configuraciones del usuario a escoger, las cuales estarán en la tabla
     * @param c Controller de los botones de la tabla de configuraciones.
     */
    public ConfigurationListPanel(ArrayList<Configuration> configs, Controller c) {
        buttonEditor = new com.dpo.centralized_restaurant.View.ListButton.ButtonEditor(new JCheckBox());
        buttonEditor.setTextButton("REMOVE");
        editButton = new JButton();
        editButton.setFocusPainted(false);

        getColumNames();
        createData(configs);
        TableModel tm = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                if(column == columnNames.length -1 || column == columnNames.length-2) return true;
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
        jtable.getColumn("Delete").setCellRenderer(new ButtonRenderer("Remove"));
        jtable.getColumn("Pick").setCellRenderer(new ButtonRenderer("Pick"));
        //jtable.getColumn("").setCellEditor(buttonEditor);
        ButtonEditor nbe = new ButtonEditor(new JCheckBox(), c, "REMOVE-CONFIGURATION", "Remove");
        ButtonEditor pbe = new ButtonEditor(new JCheckBox(), c, "PICK-THIS-CONFIGURATION", "Pick");
        jtable.getColumn("Delete").setCellEditor(nbe);
        jtable.getColumn("Pick").setCellEditor(pbe);

        this.add(jsPanel);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void registerController(Controller c){

        buttonEditor.registerController(c, "REMOVE-CONFIGURATION");
    }

    /**
     * Create the data into the table according to the current configuration
     * @param configs
     */
    public void createData(ArrayList<Configuration> configs){
        data = new Object[configs.size()][3];
        for (int i = 0; i < configs.size() ; i++){
            data[i][0] = configs.get(i).getName();
            data[i][1] = "Pick configuration";
            data[i][2] = "Delete Configuration";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Name", "Pick", "Delete"};
    }

    public String getConfigName() {
        System.out.println(jtable.getSelectedRow());
        return jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
    }
}
