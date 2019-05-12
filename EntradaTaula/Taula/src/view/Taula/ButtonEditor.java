package view.Taula;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String    label;
    private boolean   isPushed;

    public ButtonEditor(JCheckBox checkBox, Controller c, String text) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(c);
        button.setText( text);
        label = text;
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

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public void registerController(Controller c) {
        button.setActionCommand("ORDER");
        button.addActionListener(c);
    }
}
