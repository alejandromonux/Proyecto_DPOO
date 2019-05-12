package view.Taula;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer(String text) {
        setOpaque(true);
        setText( text );
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else{
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        return this;
    }
}
