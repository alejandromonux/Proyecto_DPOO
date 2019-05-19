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
    private Object[][] data ;
    private String[] columnNames;
    private JScrollPane jsPanel;
    private int comandaId;

    public DeepOrderPanel(ArrayList<RequestDish> comandas, Controller c, int comandaId) {
        this.comandaId = comandaId;
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
        jsPanel = new JScrollPane(jtable);


        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < jtable.getColumnCount();i++){
            jtable.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        jtable.getColumn("ChangeState").setCellRenderer(new ButtonRenderer("SEE MORE"));
        jtable.getColumn("ChangeState").setCellEditor(new ButtonEditor(new JCheckBox(), c, "SEE-COMANDA", "SEE MORE"));
        jtable.getColumn("Delete").setCellRenderer(new ButtonRenderer("DELETE"));
        jtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), c, "DELETE-COMANDA", "DELETE"));

        jbBack = new JButton("BACK");

        this.setLayout(new BorderLayout());
        this.add(jbBack, BorderLayout.PAGE_END);
        this.add(jsPanel, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    /**
     * Creates new data for the specific orders
     * @param comandas
     */
    public void createData(ArrayList<RequestDish> comandas ){
        data = new Object[comandas.size()][8];
        int hour;
        int min;
        for (int i =0; i < comandas.size() ; i++){
            data[i][0] = comandas.get(i).getName();
            data[i][1] = comandas.get(i).getUnits();
            data[i][2] = comandas.get(i).getActualService();
            hour = Integer.parseInt(comandas.get(i).getActivation_date().substring(12,13));
            if(Integer.parseInt(comandas.get(i).getActivation_date().substring(15,16))+ comandas.get(i).getTimecost() > 30){
                hour = Integer.parseInt(comandas.get(i).getActivation_date().substring(12,13)) + 1;
                min =(comandas.get(i).getTimecost() + Integer.parseInt(comandas.get(i).getActivation_date().substring(15,16))) - 30;
            }else{
                min =(comandas.get(i).getTimecost() + Integer.parseInt(comandas.get(i).getActivation_date().substring(15,16)));
            }
            data[i][5] = comandas.get(i).getActivation_date().substring(1,12) + hour + ":" + min + ":" + comandas.get(i).getActivation_date().substring(18,19);
            data[i][6] = "CHANGE STATE";
            data[i][7] = "DELETE";
        }
    }
    public void getColumNames(){
        columnNames = new String[]{"Name", "Units", "State", "Date", "ChangeState", "Delete"};
    }

    public void registerController(Controller c){
        jbBack.setActionCommand("BACKORDERS");
        jbBack.addActionListener(c);
    }


    public void update(ArrayList<RequestDish> comandas, Controller c){
        this.remove(jsPanel);
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
        jsPanel = new JScrollPane(jtable);


        DefaultTableCellRenderer df = new DefaultTableCellRenderer();
        df.setHorizontalAlignment(JLabel.CENTER);
        for(int i= 0; i < jtable.getColumnCount();i++){
            jtable.getColumnModel().getColumn(i).setCellRenderer(df);
        }
        jtable.getColumn("ChangeState").setCellRenderer(new ButtonRenderer("SEE MORE"));
        jtable.getColumn("ChangeState").setCellEditor(new ButtonEditor(new JCheckBox(), c, "SEE-COMANDA", "SEE MORE"));
        jtable.getColumn("Delete").setCellRenderer(new ButtonRenderer("DELETE"));
        jtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), c, "DELETE-COMANDA", "DELETE"));

        jbBack = new JButton("BACK");

        jbBack.removeActionListener(c);
        jbBack.setActionCommand("BACKORDERS");
        jbBack.addActionListener(c);

        this.removeAll();
        this.add(jbBack, BorderLayout.PAGE_END);
        this.add(jsPanel, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    public String getDishName() {
        return jtable.getValueAt(jtable.getSelectedRow(), 0).toString();
    }

    public int getUnits() {
        return (int)jtable.getValueAt(jtable.getSelectedRow(), 1);
    }

    public int getComandaId() {
        return comandaId;
    }
}
