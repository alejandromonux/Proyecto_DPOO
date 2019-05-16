package com.dpo.centralized_restaurant.Model.Configuration;

import com.dpo.centralized_restaurant.Model.Preservice.Mesa;

import javax.persistence.*;

/**
 * Handles the configuration of a table according to the general configuration of a worker
 */

@Entity
public class ConfigurationTable {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Configuration configuration;

    @ManyToOne
    Mesa mesa;

    public ConfigurationTable(){}

    public ConfigurationTable(Configuration configuration, Mesa mesa, boolean active) {
        this.configuration = configuration;
        this.mesa = mesa;
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

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
}

