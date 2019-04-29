package com.dpo.centralized_restaurant.Model.Configuration;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.persistence.*;

@Entity
public class ConfigurationDish {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Configuration configuration;

    @ManyToOne
    Dish dish;

    boolean active;

    public ConfigurationDish(){}

    public ConfigurationDish(Configuration configuration, Dish dish, boolean active) {
        this.configuration = configuration;
        this.dish = dish;
        this.active = active;
    }

    public Long getId() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ConfigurationDish{" +
                "id=" + id +
                ", configuration=" + configuration +
                ", dish=" + dish +
                ", active=" + active +
                '}';
    }
}
