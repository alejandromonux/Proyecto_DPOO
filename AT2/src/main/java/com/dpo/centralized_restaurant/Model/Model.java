package com.dpo.centralized_restaurant.Model;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Preservice.Table;
import com.dpo.centralized_restaurant.Model.Service.Comanda;

import java.util.ArrayList;

public class Model {
    private ArrayList<Dish> dishes;
    private ArrayList<Table> tables;
    private ArrayList<Comanda> comandas;

    public Model(){
        dishes = new ArrayList<Dish>();
        tables = new ArrayList<Table>();
        comandas = new ArrayList<Comanda>();
    }

    public void addDish(String dishName, String dishCost, String dishQuantity, String time) {
        dishes.add(new Dish(dishName,
                            Double.parseDouble(dishCost),
                            Integer.parseInt(dishQuantity),
                            Double.parseDouble(time)));
    }


    public void addTable(String id, String chairs) {
        tables.add(new Table(Long.parseLong(id),
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

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }

    public ArrayList<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(ArrayList<Comanda> comandas) {
        this.comandas = comandas;
    }
}
