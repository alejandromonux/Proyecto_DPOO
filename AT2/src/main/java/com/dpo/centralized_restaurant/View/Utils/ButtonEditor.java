package com.dpo.centralized_restaurant.View.Utils;

import com.dpo.centralized_restaurant.Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String    label;
    private boolean   isPushed;

    public ButtonEditor(JCheckBox checkBox, Controller c, String action) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setActionCommand(action);
        button.addActionListener(c);
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
        label = "DELETE";
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

}
