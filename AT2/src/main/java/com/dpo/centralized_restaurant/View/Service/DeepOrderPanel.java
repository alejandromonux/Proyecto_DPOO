package com.dpo.centralized_restaurant.View.Service;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Request.RequestDish;
import com.dpo.centralized_restaurant.Model.Request.RequestOrder;
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
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeepOrderPanel extends JPanel {

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

    public DeepOrderPanel(ArrayList<RequestDish> comandas, Controller c) {
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
        jtable.getColumn("ChangeState").setCellRenderer(new ButtonRenderer("SEE MORE"));
        jtable.getColumn("ChangeState").setCellEditor(new ButtonEditor(new JCheckBox(), c, "SEE-COMANDA"));
        jtable.getColumn("Delete").setCellRenderer(new ButtonRenderer("DELETE"));
        jtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), c, "SEE-COMANDA"));

        jbBack = new JButton("BACK");

        this.add(jsPanel);
        this.add(jbBack);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void createData(ArrayList<RequestDish> comandas ){
        data = new Object[comandas.size()][6];
        for (int i =0; i < comandas.size() ; i++){
            data[i][0] = comandas.get(i).getName();
            data[i][1] = comandas.get(i).getUnits();
            data[i][2] = comandas.get(i).getActualService();
            data[i][5] = "HORA ESTIMADA ENTREGA";
            data[i][6] = "CHANGE STATE";
            data[i][7] = "DELETE";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Name", "Units", "State", "Date", "ChangeState", "Delete"};
    }

    public void registerController(Controller c){
        jbBack.setActionCommand("BACKSERVICE");
        jbBack.addActionListener(c);
    }
}
