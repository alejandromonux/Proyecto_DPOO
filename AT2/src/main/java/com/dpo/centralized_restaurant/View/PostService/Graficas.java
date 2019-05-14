package com.dpo.centralized_restaurant.View.PostService;



import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Graficas extends Canvas {
    private ArrayList<OrderedDish> orderedDishes;
    private int HEIGHT = 400;
    private int WIDTH = 300;
    private int[] delimitadoresY;
    private int[] posicionesX;

    public Graficas(ArrayList<OrderedDish> orderedDishes, boolean today) {
        PriorityQueue<OrderedDish> orderedDishesQueue = new PriorityQueue<OrderedDish>(new Comparator<OrderedDish>() {
            @Override
            public int compare(OrderedDish o1, OrderedDish o2) {
                return OrderedDish.compare(o1, o2);
            }
        });
        this.orderedDishes = new ArrayList<OrderedDish>();

        orderedDishesQueue.addAll(orderedDishes);

        //5 plats més demanats
        for(int i = 0; i < 5; i++){
            this.orderedDishes.add(orderedDishesQueue.poll());
        }

        //Càlcul de la posició dels delimitadors de Y i de X
        for(int i = 0; i < 5; i++){
            delimitadoresY[i] = HEIGHT-50 -(i+1)*40;
        }

        for(int i = 0; i < 5; i++){
            posicionesX[i] = WIDTH-50 -(i+1)*40;
        }


    }

    @Override
    public void paint(Graphics g){

        g.drawLine(50, 0, 50, HEIGHT); //Eje de las Y
        g.drawLine(0, 50, WIDTH, 50); //Eje de las X


        //Líneas del eje Y con los valors de veces las cuales se ha pedido un plato
        for(int i = 0; i < delimitadoresY.length; i++){
            g.setColor(Color.GRAY);
            g.drawLine(50, delimitadoresY[i], WIDTH, delimitadoresY[i]);
            g.setColor(Color.BLACK);
            g.drawString(""+this.orderedDishes.get(i).getTimesOrdered(), 30, delimitadoresY[i]);
        }

        //rectángulos de la gráfica y el label asociado a ellos
        for(int i = 0; i < posicionesX.length; i++){
            g.setColor(Color.YELLOW);
            g.drawRect(posicionesX[i],50, 40, delimitadoresY[i]);
            g.setColor(Color.BLACK);
            g.drawString(this.orderedDishes.get(i).getDishName(), posicionesX[i] , 45);
        }
    }
}
