package com.dpo.centralized_restaurant.model;


import javax.persistence.*;

import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Dish {

    @Id
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private int units;

    @Column(nullable = false)
    private double timecost;   // En segundos

    @Column(name = "historic_orders")
    private int historicOrders;

    public Dish(){
        name = "default";
        cost = 420;
        units = 69;
        timecost = 360;
        historicOrders = 3;
    }

    public Dish(String name, double cost, int units, double timecost) {
        this.name = name;
        this.cost = cost;
        this.units = units;
        this.timecost = timecost;
        this.historicOrders = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getTimecost() {
        return timecost;
    }

    public void setTimecost(int timecost) {
        this.timecost = timecost;
    }

    public int getHistoricOrders() {
        return historicOrders;
    }

    public void setHistoricOrders(int historicOrders) {
        this.historicOrders = historicOrders;
    }
/*
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
*/
    public boolean equals (Object obj) {
        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            Dish other = (Dish) obj;
            return name.equals(other.name);
        }

    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", units=" + units +
                ", timecost=" + timecost +
                ", historicOrders=" + historicOrders +
                '}';
    }
}
