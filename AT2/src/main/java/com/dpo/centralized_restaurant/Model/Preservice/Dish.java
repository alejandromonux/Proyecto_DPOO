package com.dpo.centralized_restaurant.Model.Preservice;

import com.dpo.centralized_restaurant.Model.Configuration.ConfigurationDish;

import javax.persistence.*;

import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Stores and handles the core data of the dishes, such their attributes and configurations
 */

@Entity
public class Dish {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private int units;

    @Column(nullable = false)
    private double timecost;

    @Column(name = "historic_orders")
    private int historicOrders;

    private boolean active;


    @OneToMany(mappedBy = "dish")
    Set<ConfigurationDish> configurations;

    public Dish(){}

    public Dish(String name, double cost, int units, double timecost) {
        this.name = name;
        this.cost = cost;
        this.units = units;
        this.timecost = timecost;
        this.historicOrders = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setTimecost(double timecost) {
        this.timecost = timecost;
    }

    public int getHistoricOrders() {
        return historicOrders;
    }

    public void setHistoricOrders(int historicOrders) {
        this.historicOrders = historicOrders;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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
