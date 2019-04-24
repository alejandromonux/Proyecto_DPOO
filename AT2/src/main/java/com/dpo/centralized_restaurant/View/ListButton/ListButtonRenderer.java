package com.dpo.centralized_restaurant.view.ListButton;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ListButtonRenderer extends JButton implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            this.setForeground(table.getSelectionForeground());
            this.setBackground(table.getSelectionBackground());
        } else {
            this.setForeground(table.getForeground());
            this.setBackground(UIManager.getColor("Button.background"));
        }
        return this;
    }
}
