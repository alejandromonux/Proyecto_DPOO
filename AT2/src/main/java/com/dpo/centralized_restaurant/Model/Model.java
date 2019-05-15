package com.dpo.centralized_restaurant.Model;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Service.Comanda;

import java.util.ArrayList;

public class Model {
    private ArrayList<Dish> dishes;
    private ArrayList<Mesa> mesas;
    private ArrayList<Comanda> comandas;
    private ArrayList<Configuration> configurations;

    public Model(){
        dishes = new ArrayList<Dish>();
        mesas = new ArrayList<Mesa>();
        comandas = new ArrayList<Comanda>();
    }

    /**
     * Add a dish into the ArrayList of the model from the ActionPerformed in the Controller method
     * @param dishName
     * @param dishCost
     * @param dishQuantity
     * @param time
     */
    public void addDish(String dishName, String dishCost, String dishQuantity, String time) {
        dishes.add(new Dish(dishName,
                            Double.parseDouble(dishCost),
                            Integer.parseInt(dishQuantity),
                            Double.parseDouble(time)));
    }


    /**
     * Add a table into the ArrayList of the model
     * @param id
     * @param chairs
     */
    public void addTable(String id, String chairs) {
        mesas.add(new Mesa(id,
                             Integer.parseInt(chairs)));
    }

    /**
     * Add an order into the ArrayList of the model
     * @param idTable
     * @param dishname
     * @param dishQuant
     */
    public void addComanda(int idTable, String dishname, int dishQuant){
        comandas.add(new Comanda(idTable, dishname, dishQuant));
    }

    public void addConfig(int id, String name){
        configurations.add(new Configuration(id, name));
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

    /**
     * Deletes all the dishes that has the given name
     * @param name
     */
    public void removeDish(String name) {
        boolean found = false;
        for (int i = 0; (!found) && (i < dishes.size());i++){
            if(dishes.get(i).getName().equals(name)){
                found = true;
                dishes.remove(i);
            }
        }
    }

    public ArrayList<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(ArrayList<Configuration> configurations) {
        this.configurations = configurations;
    }
}
