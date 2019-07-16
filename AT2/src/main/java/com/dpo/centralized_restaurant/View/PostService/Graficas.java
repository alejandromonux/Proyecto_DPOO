package com.dpo.centralized_restaurant.View.PostService;



import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Creates the different graphics that will be displayed to the user
 */
public class Graficas extends Canvas {
    private ArrayList<OrderedDish> orderedDishes;
    private int HEIGHT = 400;
    private int WIDTH = 500;
    private int[] delimitadoresY;
    private int[] posicionesX;

    /**
     *
     * @param orderedDishes Lista de platos para hacer la gráfica
     * @param width Anchura de la gráfica
     * @param height Altura de la gráfica
     */
    public Graficas(ArrayList<OrderedDish> orderedDishes,int width, int height) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.orderedDishes = orderedDishes;
        delimitadoresY = new int[5];
        posicionesX = new int[5];


        //Càlcul de la posició dels delimitadors de Y i de X
        for(int i = 0; i < 5; i++){
            delimitadoresY[i] = +50 +(i+1)*40;
        }

        for(int i = 0; i < 5; i++){
            posicionesX[i] = WIDTH-150 -(i+1)*50;
        }


    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g.drawLine(50, 0, 50, HEIGHT); //Eje de las Y
        //g.drawLine(0, 50, WIDTH, 50); //Eje de las X
        g.drawLine(0,  HEIGHT-100, WIDTH, HEIGHT-100); //Eje de las X


        //Líneas del eje Y con los valors de veces las cuales se ha pedido un plato
        for(int i = 0; i < orderedDishes.size(); i++){
            g.setColor(Color.GRAY);
            if(i!=orderedDishes.size()-1) {
                if (orderedDishes.get(i).getTimesOrdered() != orderedDishes.get(i+1).getTimesOrdered()) {
                    g.setColor(Color.GRAY);
                    g.drawLine(50, delimitadoresY[i], WIDTH, delimitadoresY[i]);
                    g.setColor(Color.BLACK);
                    g.drawString(""+this.orderedDishes.get(i).getTimesOrdered(), 30, delimitadoresY[i]);
                }
                if(i!=0){
                    if(orderedDishes.get(i).getTimesOrdered() != orderedDishes.get(i-1).getTimesOrdered()){
                        g.setColor(Color.GRAY);
                        g.drawLine(50, delimitadoresY[i], WIDTH, delimitadoresY[i]);
                        g.setColor(Color.BLACK);
                        g.drawString(""+this.orderedDishes.get(i).getTimesOrdered(), 30, delimitadoresY[i]);
                    }
                }
            }else{
                if (orderedDishes.get(i).getTimesOrdered() != orderedDishes.get(i-1).getTimesOrdered()) {
                    g.setColor(Color.GRAY);
                    g.drawLine(50, delimitadoresY[i], WIDTH, delimitadoresY[i]);
                    g.setColor(Color.BLACK);
                    g.drawString(""+this.orderedDishes.get(i).getTimesOrdered(), 30, delimitadoresY[i]);
                }else{
                    g.setColor(Color.GRAY);
                    g.drawLine(50, delimitadoresY[0], WIDTH, delimitadoresY[0]);
                    g.setColor(Color.BLACK);
                    g.drawString(""+this.orderedDishes.get(0).getTimesOrdered(), 30, delimitadoresY[0]);
                }
             }
            g.setColor(Color.BLACK);
        }

        //rectángulos de la gráfica y el label asociado a ellos
        for(int i = 0; i < orderedDishes.size(); i++){
            g.setColor(Color.YELLOW);
            if(i!=0){
                boolean found = false;
                int index = 0;
                for (int j = 0;(j < orderedDishes.size())&&!found; j++){
                    if(orderedDishes.get(j).getTimesOrdered() == orderedDishes.get(i).getTimesOrdered() ){
                        found = true;
                        index = j;
                    }
                }
                if(!found){
                    index = i;
                }
                /*
                if(orderedDishes.get(i).getTimesOrdered() == orderedDishes.get(i-1).getTimesOrdered()){
                    g2.fillRect(posicionesX[i], delimitadoresY[i-1], 30, HEIGHT - delimitadoresY[i-1]-100);
                }else{
                    g2.fillRect(posicionesX[i], delimitadoresY[i], 30, HEIGHT - delimitadoresY[i]-100);
                }
                */
                g2.fillRect(posicionesX[i], delimitadoresY[index], 30, HEIGHT - delimitadoresY[index]-100);

            }else{
                g2.fillRect(posicionesX[i], delimitadoresY[i], 30, HEIGHT - delimitadoresY[i]-100);
            }
            g.setColor(Color.BLACK);
            g.drawString(this.orderedDishes.get(i).getDishName(), posicionesX[i] , HEIGHT-85);
        }
    }
}
