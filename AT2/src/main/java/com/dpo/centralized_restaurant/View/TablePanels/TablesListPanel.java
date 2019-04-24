package com.dpo.centralized_restaurant.View.TablePanels;

//import sun.tools.jps.Jps;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
public class TablesListPanel extends JPanel {

    private JTextField jtfId;
    private JComboBox jcbQuantity;
    private JButton jbAdd;

    public TablesListPanel() {


        JPanel jpTable = new JPanel(new FlowLayout());
        JTable jtableStats;
        Object[][] data;

        String[] columnNames = {"Completed Tasks",
                "Date",
                "Earned",};

        data = new Object[][]{
                {"Kathy", "Smith", "Snowboarding"},
                {"John", "Doe", "Rowing"},
                {"Sue", "Black", "Knitting"},
                {"Jane", "White", "Speed reading"},
                {"Joe", "Brown", "Pool"}
        };

        jtableStats = new JTable(data, columnNames);
        jpTable.add(jtableStats);
        add(jpTable);
        setVisible(true);

    }

}
*/

public class TablesListPanel extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {

    private JTable table;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;

    public TablesListPanel(JTable table, Action action, int column) {
        this.table = table;
        this.action = action;

        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted( false );
        editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder( new LineBorder(Color.BLUE) );

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
        table.addMouseListener( this );
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public void setMnemonic(int mnemonic) {
        this.mnemonic = mnemonic;
        renderButton.setMnemonic(mnemonic);
        editButton.setMnemonic(mnemonic);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            editButton.setText( "" );
            editButton.setIcon( null );
        }else if (value instanceof Icon) {
            editButton.setText( "" );
            editButton.setIcon( (Icon)value );
        }else{
            editButton.setText( value.toString() );
            editButton.setIcon( null );
        }

        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }else{
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus){
            renderButton.setBorder( focusBorder );
        }else{
            renderButton.setBorder( originalBorder );
        }

        if (value == null){
            renderButton.setText( "" );
            renderButton.setIcon( null );
        }else if(value instanceof Icon){
            renderButton.setText( "" );
            renderButton.setIcon( (Icon)value );
        }else{
            renderButton.setText( value.toString() );
            renderButton.setIcon( null );
        }

        return renderButton;
    }


    public void actionPerformed(ActionEvent e){
        int row = table.convertRowIndexToModel( table.getEditingRow() );
        fireEditingStopped();

        ActionEvent event = new ActionEvent(
                table,
                ActionEvent.ACTION_PERFORMED,
                "" + row);
        action.actionPerformed(event);
    }


    public void mousePressed(MouseEvent e){
        if (table.isEditing()
                &&  table.getCellEditor() == this)
            isButtonColumnEditor = true;
    }

    public void mouseReleased(MouseEvent e){
        if (isButtonColumnEditor
                &&  table.isEditing())
            table.getCellEditor().stopCellEditing();

        isButtonColumnEditor = false;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
