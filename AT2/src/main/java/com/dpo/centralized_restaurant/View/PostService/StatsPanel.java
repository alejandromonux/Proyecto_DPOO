package com.dpo.centralized_restaurant.View.PostService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class StatsPanel extends JPanel {
    private JLabel gananciaHoy;
    private JLabel gananciaTotal;
    private JLabel mitjanaPlatsTaules;
    private JLabel preuMigTaula;

    public StatsPanel(long gananciaDia, long gananciaTotal, int mitjanaPlatsTaules, float preuMigTaula){
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
