package com.dpo.centralized_restaurant.View.PostService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Creates and displays the panel that will give information about the stats to the user
 */
public class StatsPanel extends JPanel {
    private JLabel gananciaHoy;
    private JLabel gananciaTotal;
    private JLabel mitjanaPlatsTaules;
    private JLabel preuMigTaula;

    /**Creación del panel de estadísticas
     *
     * @param gananciaDia float de ganancia del día a meter en una JLabel
     * @param gananciaTotal float de ganancia total del restaurante a meter en una JLabal
     * @param mitjanaPlatsTaules float de mediana de platos por tabla a meter en una JLabel
     * @param preuMigTaula float de mediana de precio por mesa a meter en una JLabel
     */
    public StatsPanel(float gananciaDia, float gananciaTotal, float mitjanaPlatsTaules, float preuMigTaula){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        TitledBorder title = BorderFactory.createTitledBorder("Estadistiques");
        title.setTitleJustification(TitledBorder.LEFT);
        //this.setBorder(title);
        this.gananciaHoy = new JLabel("Today's profit: " + gananciaDia);
        if(gananciaTotal != 0){
            this.gananciaTotal = new JLabel("Total profit: " + gananciaTotal);
        }
        this.mitjanaPlatsTaules = new JLabel("Average of dishes per table: " + mitjanaPlatsTaules);
        this.preuMigTaula = new JLabel("Average money paid per table: " + preuMigTaula);

        this.add(gananciaHoy);
        if(gananciaTotal != 0){
            this.add(this.gananciaTotal);
        }
        this.add(this.mitjanaPlatsTaules);
        this.add(this.preuMigTaula);
    }

}
