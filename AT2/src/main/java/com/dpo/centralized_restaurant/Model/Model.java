package com.dpo.centralized_restaurant.Model;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Service.Comanda;

import java.util.ArrayList;

public class Model {
    private ArrayList<Dish> dishes;
    private ArrayList<Mesa> mesas;
    private ArrayList<Comanda> comandas;

    public Model(){
        dishes = new ArrayList<Dish>();
        mesas = new ArrayList<Mesa>();
        comandas = new ArrayList<Comanda>();
    }

    public void addDish(String dishName, String dishCost, String dishQuantity, String time) {
        dishes.add(new Dish(dishName,
                            Double.parseDouble(dishCost),
                            Integer.parseInt(dishQuantity),
                            Double.parseDouble(time)));
    }


    public void addTable(String id, String chairs) {
        mesas.add(new Mesa(Long.parseLong(id),
                             Integer.parseInt(chairs)));
    }

    public void addComanda(long idTable, String dishname, int dishQuant){
        comandas.add(new Comanda(idTable, dishname, dishQuant));
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public ArrayList<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(ArrayList<Mesa> mesas) {
        this.mesas = mesas;
    }

    public ArrayList<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(ArrayList<Comanda> comandas) {
        this.comandas = comandas;
    }
}
