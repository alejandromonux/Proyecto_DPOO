package com.dpo.centralized_restaurant.Model.Configuration;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.persistence.*;

@Entity
public class ConfigurationDish {
    @Id
    @Embedded
    ConfigurationDishKey id;

    @ManyToOne
    @MapsId("configuration_id")
    @JoinColumn(name = "configuration_id")
    Configuration configuration;

    @ManyToOne
    @MapsId("dish_id")
    @JoinColumn(name = "dish_id")
    Dish dish;

    boolean active;

    public ConfigurationDish(){}

    public ConfigurationDish(ConfigurationDishKey id, Configuration configuration, Dish dish, boolean active) {
        this.id = id;
        this.configuration = configuration;
        this.dish = dish;
        this.active = active;
    }

    public ConfigurationDishKey getId() {
        return id;
    }

    public void setId(ConfigurationDishKey id) {
        this.id = id;
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
                ", Configuration=" + configuration +
                ", dish=" + dish +
                ", active=" + active +
                '}';
    }
}
