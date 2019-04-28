package com.dpo.centralized_restaurant.View.TablePanels;

//import sun.tools.jps.Jps;

import com.dpo.centralized_restaurant.View.Utils.ButtonEditor;
import com.dpo.centralized_restaurant.View.Utils.ButtonRenderer;
import com.dpo.centralized_restaurant.Model.Preservice.Table;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class TablesListPanel extends JPanel{

    private JTable jtable;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    Object[][] data ;
    String[] columnNames;

    public TablesListPanel(ArrayList<Table> tables) {

        //renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        //editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));

        getColumNames();
        createData(tables);
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
        jtable.getColumn("").setCellRenderer(new ButtonRenderer());
        jtable.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));

        this.add(jsPanel);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void createData(ArrayList<Table> tables){
        data = new Object[tables.size()][3];
        for (int i =0; i < tables.size() ; i++){
            data[i][0] = tables.get(i).getId();
            data[i][1] = tables.get(i).getChairs();
            data[i][2] = "Delete table";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Identifier", "Chairs", ""};
    }
}
