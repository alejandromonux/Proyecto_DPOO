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
