package view.Taula;

import controller.Controller;
import model.RequestDish;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class ComandaPanel extends JPanel {

    private final Color cAux = new Color(0x1A0D08);

    private ArrayList<RequestDish> comanda;

    private JScrollPane jScrollPane;
    private JTable jpContent;
    private Object[][] data;
    private String[] columnNames = { "Name", "Cost", "Units", "TimeToServe", "" };
    private TableModel model;

    private JButton jbtnBack;
    private ButtonEditor jbtnRemove;

    public ComandaPanel() {

        comanda = new ArrayList<>();
        //dishCreator();      // INVENTED DATA
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

    /*public void dishCreator() {
        RequestDish dish = new RequestDish(Integer.MAX_VALUE, 1,"Arros",10,5, 6,"");
        RequestDish dish1 = new RequestDish(Integer.MAX_VALUE,2,"Patates fregides",10,3, 5,"");
        RequestDish dish2 = new RequestDish(Integer.MAX_VALUE,3,"Llenguado",20,4, 4,"");
        RequestDish dish3 = new RequestDish(Integer.MAX_VALUE, 4,"Sopa",15,6, 3,"");
        RequestDish dish4 = new RequestDish(Integer.MAX_VALUE, 5,"Bacalla", 10.0,7, 2,"");

        comanda.add(dish);
        comanda.add(dish1);
        comanda.add(dish2);
        comanda.add(dish3);
        comanda.add(dish4);
        comanda.add(dish);
        comanda.add(dish1);
        comanda.add(dish2);
        comanda.add(dish3);
        comanda.add(dish4);
        comanda.add(dish);
        comanda.add(dish1);
        comanda.add(dish2);
        comanda.add(dish3);
        comanda.add(dish4);


        data = new Object[comanda.size()][columnNames.length];
        for (int i = 0; i < data.length;i++) {
            Object[] obj = new Object[]{
                    comanda.get(i).getName(),
                    comanda.get(i).getCost(),
                    comanda.get(i).getUnits(),
                    comanda.get(i).getTimecost(),
                    jbtnRemove
            };
            data[i] = obj;
        }
    }*/

    public void registerController(Controller c) {
        jbtnBack.setActionCommand("BACK-TO-ORDERS");
        jbtnBack.addActionListener(c);
        jbtnRemove = new ButtonEditor(new JCheckBox(), c, "REMOVE");
        jpContent.getColumn("").setCellRenderer(new ButtonRenderer("REMOVE-FROM-ORDER"));
        jpContent.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), c, "REMOVE-FROM-ORDER"));
        jbtnRemove.registerController(c, "REMOVE-FROM-ORDER");
    }

    public void updateMenu(ArrayList<RequestDish> menu) {

        data = new Object[menu.size()][columnNames.length];
        for (int i = 0; i < data.length;i++) {
            Object[] obj = new Object[]{
                    menu.get(i).getName(),
                    menu.get(i).getCost(),
                    menu.get(i).getUnits(),
                    menu.get(i).getTimecost(),
                    jbtnRemove
            };
            data[i] = obj;
        }

        tableCreator();
        scrollPaneConfig(jScrollPane);

        this.removeAll();
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(jbtnBack, BorderLayout.PAGE_END);
    }

    public void addOrderToCart(RequestDish dish) {

        boolean exists = false;
        int index = 0;
        for (int i = 0; i < comanda.size();i++) {
            RequestDish d = comanda.get(i);
            if (d.getName().equalsIgnoreCase(dish.getName())) {
                exists = true;
                index = i;
            }
        }
        if (exists) {
            comanda.get(index).setUnits(comanda.get(index).getUnits()+1);
        } else {
            dish.setUnits(1);
            comanda.add(dish);
        }

        updateMenu(comanda);
    }

    public void removeOrderFromCart(int index) {

        if (comanda.get(index).getUnits() > 1) {
            comanda.get(index).setUnits(comanda.get(index).getUnits()-1);
        } else {
            comanda.remove(index);
        }
        updateMenu(comanda);
    }

    public ArrayList<RequestDish> getBagOfOrders() {
        return comanda;
    }

    public int getSelectedComanda() {
        return jpContent.getSelectedRow();
    }

    public void clearComandes() {
        comanda = new ArrayList<>();
        updateMenu(comanda);
    }
}
