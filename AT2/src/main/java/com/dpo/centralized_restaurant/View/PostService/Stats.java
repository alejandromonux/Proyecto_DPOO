package com.dpo.centralized_restaurant.View.PostService;

import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Stats extends JPanel {
    Canvas grafica;
    Canvas graficaAll;
    JPanel stats;

    public Stats(ArrayList<OrderedDish> dish, ArrayList<OrderedDish> dishtotal,
        long today, long total, int platsTaules, float preuMig ){
        grafica = new Graficas(dish, true);
        graficaAll = new Graficas(dishtotal, false);
        stats = new StatsPanel(today, total, platsTaules, preuMig);

        this.setLayout(new CardLayout());

        this.add("TODAYGRAPH",grafica);
        this.add("TOTALGRAPH", graficaAll);
        this.add("STATS", stats);
    }
}
