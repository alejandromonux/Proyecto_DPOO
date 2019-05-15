package com.dpo.centralized_restaurant.Controller;

import com.dpo.centralized_restaurant.Model.Model;
import com.dpo.centralized_restaurant.View.MainView;


import javax.swing.*;
import java.awt.event.MouseEvent;


/**
 * Controls the interaction between the user and the mouse within the views
 */
public class MouseListener implements java.awt.event.MouseListener {
    Model m;
    MainView v;

    /**
     * In case the user presses the button within the table/dish table, it does a certain action
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        JTable element = (JTable)e.getSource();
        int row = element.getSelectedRow();
        int column = element.getSelectedColumn();
        String name = (String) element.getModel().getValueAt(row, 0);
        m.removeDish(name);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
