package com.dpo.centralized_restaurant.View.TablePanels;

//import sun.tools.jps.Jps;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.View.Utils.ButtonRenderer;
import com.dpo.centralized_restaurant.View.Utils.ButtonEditor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates the right size of panel that the user will see in the view, this being a list of the tables
 */
public class TablesListPanel extends JPanel{

    private JTable jtable;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    private Object[][] data ;
    private String[] columnNames;
    private com.dpo.centralized_restaurant.View.ListButton.ButtonEditor buttonEditor;
    private JScrollPane jsPanel;

    /**
     *
     * @param mesas Lista de comandas a meter en las tablas
     * @param c Controller para las tablas
     */
    public TablesListPanel(ArrayList<Mesa> mesas, Controller c) {
        buttonEditor = new com.dpo.centralized_restaurant.View.ListButton.ButtonEditor(new JCheckBox());
        //renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        //editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));

        getColumNames();
        createData(mesas);
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
        jtable.getColumn("").setCellRenderer(new ButtonRenderer("Remove"));
        //jtable.getColumn("").setCellEditor(buttonEditor);
        ButtonEditor nbe = new ButtonEditor(new JCheckBox(), c, "REMOVE-TABLE", "Remove");
        jtable.getColumn("").setCellEditor(nbe);

        this.add(jsPanel);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    /**
     *
     * @param c Controller a registrar en los botones
     */
    public void registerController(Controller c){
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    /**
     * Creates new data for the specific orders
     * @param mesas mesas a meter en la tabla
     */
    public void createData(ArrayList<Mesa> mesas){
        data = new Object[mesas.size()][3];
        for (int i = 0; i < mesas.size() ; i++){
            data[i][0] = mesas.get(i).getId();
            data[i][1] = mesas.get(i).getChairs();
            data[i][2] = "Delete table";
        }
    }

    /**
     * Asigna los nombres a las columnas de la tabla
     */
    public void getColumNames(){
        columnNames = new String[]{"Identifier", "Chairs", ""};
    }

    public String getTableName() {
        return jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
    }

    /** Actualización de la tabla
     *
     * @param mesas mesas a poner en la tabla
     * @param c Controller de los botones de la tabla
     */
    public void update(ArrayList<Mesa> mesas, Controller c){
        this.remove(jsPanel);

        buttonEditor = new com.dpo.centralized_restaurant.View.ListButton.ButtonEditor(new JCheckBox());
        //renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        //editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));

        getColumNames();
        createData(mesas);
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
        jtable.getColumn("").setCellRenderer(new ButtonRenderer("Remove"));
        //jtable.getColumn("").setCellEditor(buttonEditor);
        ButtonEditor nbe = new ButtonEditor(new JCheckBox(), c, "REMOVE-TABLE", "Remove");
        jtable.getColumn("").setCellEditor(nbe);

        this.add(jsPanel);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }
}
