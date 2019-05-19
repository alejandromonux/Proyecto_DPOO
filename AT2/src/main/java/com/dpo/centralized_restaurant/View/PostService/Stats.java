package com.dpo.centralized_restaurant.View.PostService;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Displays the graphics that will be displayed as part of the stats
 */
public class Stats extends JPanel {
    private Canvas grafica;
    private Canvas graficaAll;
    private JPanel stats;
    private JPanel content;
    private JButton jbToday;
    private JButton jbTotal;
    private JButton jbStats;
    private JButton jbBack;
    private CardLayout LContent;

    /**
     *
     * @param dish Lista de platos para la gráfica del día actual
     * @param dishtotal Lista de platos para la gráfica del servicio total
     * @param today float de ganancia del día a meter en una JLabel
     * @param total float de ganancia total del restaurante a meter en una JLabal
     * @param platsTaules float de mediana de platos por tabla a meter en una JLabel
     * @param preuMig float de mediana de precio por mesa a meter en una JLabel
     */
    public Stats(ArrayList<OrderedDish> dish, ArrayList<OrderedDish> dishtotal,
        float today, float total, float platsTaules, float preuMig ){

        JPanel jpLeft = new JPanel(new BorderLayout(0,15));
        jpLeft.setBackground(null);

        JPanel jpBigLeft = new JPanel(new BorderLayout());
        jpBigLeft.setBorder(new EmptyBorder(0, 50, 90, 50));
        jpBigLeft.setSize(800,250);

        JLabel title = new JLabel("STADISTICS");
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Chaparral Pro Light", Font.PLAIN, 25));


        JPanel jpAuxH1 = new JPanel(new BorderLayout());
        jbToday = new JButton("TODAY");
        jbToday.setBackground(Color.white);
        jbToday.setFocusable(false);
        jpAuxH1.add(jbToday);
        jbToday.setBorder(new EmptyBorder(10, 70, 10, 70));


        JPanel jpAuxH2 = new JPanel(new BorderLayout());
        jbTotal = new JButton("TOTAL");
        jbTotal.setBackground(Color.white);
        jbTotal.setFocusable(false);
        jbTotal.setBorder(new EmptyBorder(10, 70, 10, 70));
        jpAuxH2.add(jbTotal);
        //jpAuxH2.setBorder(new EmptyBorder(40, 0, 40, 0));


        JPanel jpAuxH3 = new JPanel(new BorderLayout());
        jbStats = new JButton("STATS");
        jbStats.setBorder(new EmptyBorder(20, 40, 20, 40));
        jbStats.setBackground(Color.white);
        jbStats.setFocusable(false);
        jpAuxH3.add(jbStats);
        jpAuxH3.setBorder(new EmptyBorder(20, 0, 20, 0));
        jpAuxH3.setBackground(null);

        JPanel jpAuxH4 = new JPanel(new BorderLayout());
        jbBack = new JButton("BACK");
        jbBack.setBorder(new EmptyBorder(10, 40, 20, 40));
        jbBack.setBackground(Color.white);
        jbBack.setFocusable(false);
        jpAuxH4.add(jbBack);
        jpAuxH4.setBorder(new EmptyBorder(10, 0, 70, 0));
        jpAuxH4.setBackground(null);


        jpLeft.add(jpAuxH1, BorderLayout.PAGE_START);
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BorderLayout());
        panelCenter.setBackground(null);
        panelCenter.add(jpAuxH2, BorderLayout.PAGE_START);
        panelCenter.add(jpAuxH3, BorderLayout.CENTER);
        jpLeft.add(panelCenter, BorderLayout.CENTER);
        //jpLeft.add(jpAuxH3, BorderLayout.EAST);
        jpLeft.add(jpAuxH4, BorderLayout.PAGE_END);

        jpBigLeft.add(title, BorderLayout.PAGE_START);
        jpBigLeft.add(jpLeft, BorderLayout.CENTER);


        grafica = new Graficas(dish, (int) this.getSize().getWidth() - jpBigLeft.getSize().width,this.getHeight()-100 );
        graficaAll = new Graficas(dishtotal, (int) this.getSize().getWidth() - jpBigLeft.getSize().width,this.getHeight()-100 );
        stats = new StatsPanel(today, total, platsTaules, preuMig);

        content = new JPanel();
        JPanel graficaAux = new JPanel();
        JPanel graficaAux2 = new JPanel();
        graficaAux2.add(graficaAll);
//        grafica.setPreferredSize(new Dimension(grafica.getWidth(), grafica.getHeight()));
        graficaAux.add(grafica);
        LContent = new CardLayout();
        content.setLayout(LContent);
        //graficaAux.setSize(new Dimension((int)(this.getSize().getWidth() - jpBigLeft.getSize().width), this.getHeight()-100));
        //graficaAux2.setSize(new Dimension((int)(this.getSize().getWidth() - jpBigLeft.getSize().width), this.getHeight()-100));
        content.add("TODAYGRAPH",graficaAux);
        content.add("TOTALGRAPH", graficaAux2);
        stats.setBorder(new EmptyBorder(100,0,0,0));
        content.add("STATS", stats);
        content.setBorder(new EmptyBorder(0,300, 0, 250));
        //content.setSize(new Dimension((int)(this.getSize().getWidth() - jpBigLeft.getSize().width), this.getHeight()-100));

        jpBigLeft.setBackground(new Color(0x974807));
        setLayout(new SpringLayout());
        setSize(800,250);
        add(jpBigLeft);
        add(content);
    }

    /**
     *
     * @param c Controller para los botones
     */
    public void registerController(Controller c){
        jbToday.setActionCommand("TODAYGRAPH");
        jbToday.addActionListener(c);

        jbTotal.setActionCommand("TOTALGRAPH");
        jbTotal.addActionListener(c);

        jbStats.setActionCommand("STATSPANEL");
        jbStats.addActionListener(c);

        jbBack.setActionCommand("BACK-TO-PS");
        jbBack.addActionListener(c);
    }

    public void changePanel(String todaygraph) {
        LContent.show(content, todaygraph);
    }
}
