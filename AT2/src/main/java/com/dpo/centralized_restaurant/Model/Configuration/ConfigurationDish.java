package com.dpo.centralized_restaurant.Model.Configuration;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.persistence.*;

/**
 * Handles the configuration of the dishes according to the general configuration of a worker
 */

@Entity
public class ConfigurationDish {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Configuration configuration;

    @ManyToOne
    private Dish dish;

    public ConfigurationDish(){}

    public ConfigurationDish(Configuration configuration, Dish dish, boolean active) {
        this.configuration = configuration;
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public String toString() {
        return "ConfigurationDish{" +
                "id=" + id +
                ", configuration=" + configuration +
                ", dish=" + dish +
                '}';
    }
}
