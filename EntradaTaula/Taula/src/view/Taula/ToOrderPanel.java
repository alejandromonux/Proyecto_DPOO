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

public class ToOrderPanel extends JPanel {

    private final Color cAux = new Color(0x1A0D08);

    private ArrayList<Dish> dishes;

    private JScrollPane jScrollPane;
    private JTable jpContent;
    private Object[][] data;
    private String[] columnNames = { "Name", "Cost", "Units", "TimeCost", "" };
    private TableModel model;

    private JButton jbtnBack;
    private ButtonEditor jbtnOrder;

    public ToOrderPanel() {

        dishes = new ArrayList<>();
        dishCreator();      // INVENTED DATA
        tableCreator();

        JPanel jpButton = new JPanel();
        jbtnBack = new JButton("BACK");
        jpButton.add(jbtnBack);
        jpButton.setBorder(new EmptyBorder(10,0,0,0));

        jpButton.setBackground(cAux);
        scrollPaneConfig(jScrollPane);

        this.setBackground(cAux);
        this.setBorder(new EmptyBorder(0,10,0,10));
        this.setLayout(new BorderLayout(0,10));
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(jbtnBack, BorderLayout.PAGE_END);

    }

    private void tableCreator() {
        model = new DefaultTableModel(data,columnNames) {
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
    }

    private void scrollPaneConfig(JScrollPane jsp) {
        Border border = BorderFactory.createEmptyBorder( 0, 0, 0, 0 );
        jsp.setViewportBorder( border );
        jsp.setBorder( border );
        jsp.setBackground(cAux);
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
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


        data = new Object[dishes.size()][columnNames.length];
        for (int i = 0; i < data.length;i++) {
            Object[] obj = new Object[]{
                    dishes.get(i).getName(),
                    dishes.get(i).getCost(),
                    dishes.get(i).getUnits(),
                    dishes.get(i).getTimecost(),
                    jbtnOrder
            };
            data[i] = obj;
        }
    }

    public void registerController(Controller c) {
        jbtnBack.setActionCommand("BACK");
        jbtnBack.addActionListener(c);
        jbtnOrder = new ButtonEditor(new JCheckBox(), c, "ORDER");
        jpContent.getColumn("").setCellRenderer(new ButtonRenderer("ORDER"));
        jpContent.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), c, "ORDER"));
        jbtnOrder.registerController(c);
    }

    public void updateMenu(ArrayList<Dish> menu) {

        data = new Object[menu.size()][columnNames.length];
        for (int i = 0; i < data.length;i++) {
                Object[] obj = new Object[]{
                        menu.get(i).getName(),
                        menu.get(i).getCost(),
                        menu.get(i).getUnits(),
                        menu.get(i).getTimecost(),
                        jbtnOrder
                };
                data[i] = obj;
        }

        tableCreator();
        scrollPaneConfig(jScrollPane);

        this.removeAll();
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(jbtnBack, BorderLayout.PAGE_END);
    }

    public String getSelectedDish() {
        return jpContent.getValueAt(jpContent.getSelectedRow(), 0).toString();
    }
}
