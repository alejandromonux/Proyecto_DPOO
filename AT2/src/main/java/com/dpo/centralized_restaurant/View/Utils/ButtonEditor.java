package com.dpo.centralized_restaurant.View.Utils;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Sets the configuration of the button that is within the JTables, thus allowing user to interact with it
 */
public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String    label;
    private boolean   isPushed;

    public ButtonEditor(JCheckBox checkBox, ActionListener al, String action, String label) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setActionCommand(action);
        if (button.getActionListeners().length > 0) {
            button.removeActionListener(al);
        }
        button.addActionListener(al);
        this.label = label;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public void  registerController(Controller c, String command) {
        if (button.getActionListeners().length > 0) {
            button.removeActionListener(c);
        }
        button.setActionCommand(command);
        button.addActionListener(c);
    }

}
