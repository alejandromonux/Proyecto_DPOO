package com.dpo.centralized_restaurant.View.ListButton;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Sets the configuration of the button that is within the JTables, thus allowing user to interact with it
 */
public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String    label;
    private boolean   isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setText( "REMOVE" );
    }
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else{
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = "REMOVE";
        button.setText( label );
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed)  {
            System.out.println("Aquii");    
        }
        isPushed = false;
        return new String( label ) ;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public void  registerController(Controller c, String command) {
        button.setActionCommand(command);
        button.addActionListener(c);
    }

    public void setTextButton(String text){
        button.setText(text);
    }
}