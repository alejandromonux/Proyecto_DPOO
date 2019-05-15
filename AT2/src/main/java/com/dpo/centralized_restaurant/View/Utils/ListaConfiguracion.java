package com.dpo.centralized_restaurant.View.Utils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * Sets the configuration of the list with the different configurations set up
 */
public class ListaConfiguracion extends JPanel {

    private DefaultListModel infoLlista = new DefaultListModel();

    private JList llistaConfiguracio = new JList(infoLlista);

    public ListaConfiguracion(){

        llistaConfiguracio.setLayoutOrientation(JList.VERTICAL);
        llistaConfiguracio.setVisibleRowCount(-1);
        add(llistaConfiguracio);

        setVisible(true);

    }

    public void add(String nou){

        infoLlista.addElement(nou);
        revalidate();
    }

    public void RegisterMouseListenerList(MouseAdapter al){

        llistaConfiguracio.addMouseListener(al);

        /* Codigo para el doble clic
            debera ser añadido des del controller
            -------------------------------------------------------------------
            list.addMouseListener(new MouseAdapter() {
                 public void mouseClicked(MouseEvent evt) {
                    JList list = (JList)evt.getSource();
                    if (evt.getClickCount() == 2) {

                        // Double-click detected
                        int index = list.locationToIndex(evt.getPoint());
                    } else if (evt.getClickCount() == 3) {

                        // Triple-click detected
                        int index = list.locationToIndex(evt.getPoint());
                    }
                }
            });
            --------------------------------------------------------------------

         */

    }


}
