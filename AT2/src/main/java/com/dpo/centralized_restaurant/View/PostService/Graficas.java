package com.dpo.centralized_restaurant.View.PostService;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Graficas extends JPanel {


    public Graficas(double []values, int num) {

        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("series label",values,num);

        JFreeChart chart = ChartFactory.
                createHistogram( "plotTitle", "xaxis label", "yaxis label",
                        dataset, PlotOrientation.VERTICAL, false, false, false);

        ChartPanel Panel = new ChartPanel(chart);
        Panel.setPreferredSize(new Dimension (500, 270));
        Panel.setMouseZoomable(true, false);

        //ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);

    }
}
