package com.dpo.centralized_restaurant.Model;

import com.dpo.centralized_restaurant.Model.Table;

import javax.swing.*;
import java.util.ArrayList;
import com.dpo.centralized_restaurant.Model.*;

public class Model {
    private ArrayList<com.dpo.centralized_restaurant.model.Dish> dishes;
    private ArrayList<Table> tables;

    public Model(){
        dishes = new ArrayList<com.dpo.centralized_restaurant.model.Dish>();
        tables = new ArrayList<Table>();
    }

    public void addDish(String dishName, String dishCost, String dishQuantity, String time) {
        dishes.add(new com.dpo.centralized_restaurant.model.Dish(dishName,
                            Double.parseDouble(dishCost),
                            Integer.parseInt(dishQuantity),
                            Double.parseDouble(time)));
    }


    public void addTable(String id, String chairs) {
        tables.add(new Table(Long.parseLong(id),
                             Integer.parseInt(chairs)));
    }

    public ArrayList<com.dpo.centralized_restaurant.model.Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<com.dpo.centralized_restaurant.model.Dish> dishes) {
        this.dishes = dishes;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }

}
