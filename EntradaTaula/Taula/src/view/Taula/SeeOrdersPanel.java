package view.Taula;

import controller.Controller;
import model.Dish;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class SeeOrdersPanel extends JPanel {

    private final Color cAux = new Color(0x1A0D08);

    private ArrayList<Dish> dishes;

    private JScrollPane jScrollPane;
    private JTable jpContent;
    private Object[][] data;
    private String[] columnNames = { "Name", "Cost", "Units", "TimeCost", "" };

    private JButton jbtnBack;
    private ButtonEditor jbtnDelete;

    public SeeOrdersPanel() {

        dishes = new ArrayList<>();
        dishCreator();

        TableModel model = new DefaultTableModel(data,columnNames) {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                if (mColIndex == columnNames.length-1) return true;
                return false;
            }
        };
        jpContent = new JTable(model);
        jpContent.setGridColor(cAux);
        jpContent.setRowHeight(30);
        jScrollPane = new JScrollPane(jpContent);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < jpContent.getColumnCount(); i++) {
            jpContent.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JPanel jpButton = new JPanel();
        jbtnBack = new JButton("BACK");
        jpButton.add(jbtnBack);
        jpButton.setBorder(new EmptyBorder(10,0,0,0));

        jpButton.setBackground(cAux);
        scrollPaneConfig();

        this.setBackground(cAux);
        this.setBorder(new EmptyBorder(0,10,0,10));
        this.setLayout(new BorderLayout(0,10));
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(jbtnBack, BorderLayout.PAGE_END);

    }

    private void scrollPaneConfig() {
        Border border = BorderFactory.createEmptyBorder( 0, 0, 0, 0 );
        jScrollPane.setViewportBorder( border );
        jScrollPane.setBorder( border );
        jScrollPane.setBackground(cAux);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setOpaque(false);
    }

    public void dishCreator() {
        Dish dish = new Dish("Arros", 7.9,10,5);
        Dish dish1 = new Dish("Patates fregides", 2.9,10,3);
        Dish dish2 = new Dish("Llenguado", 2.9,20,4);
        Dish dish3 = new Dish("Sopa", 5.9,15,6);
        Dish dish4 = new Dish("Bacalla", 4.9,10,7);

        dishes.add(dish);
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish4);
        dishes.add(dish);
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish4);
        dishes.add(dish);
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish4);
        dishes.add(dish);
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish4);

        data = new Object[dishes.size()][columnNames.length];
        for (int i = 0; i < data.length;i++) {
            Object[] obj = new Object[]{
                    dishes.get(i).getName(),
                    dishes.get(i).getCost(),
                    dishes.get(i).getUnits(),
                    dishes.get(i).getTimecost(),
                    jbtnDelete
            };
            data[i] = obj;
        }
    }

    public void registerController(Controller c) {
        jbtnBack.setActionCommand("BACK");
        jbtnBack.addActionListener(c);
        jbtnDelete = new ButtonEditor(new JCheckBox(), c, "DELETE");

        ButtonRenderer btnRenderer = new ButtonRenderer("DELETE");

        jpContent.getColumn("").setCellRenderer(btnRenderer);
        jpContent.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), c, "DELETE"));
        jbtnDelete.registerController(c);
    }

    public void updateDishes(ArrayList<Dish> dishes) {
    }

    public String getDishToDelete() {
        return jpContent.getValueAt(jpContent.getSelectedRow(), 0).toString();
    }
}
