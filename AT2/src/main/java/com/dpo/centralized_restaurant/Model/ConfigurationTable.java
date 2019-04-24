package com.dpo.centralized_restaurant.model;

import javax.persistence.*;

@Entity
public class ConfigurationTable {
    @Id
    @Embedded
    ConfigurationTableKey id;

    @ManyToOne
    @MapsId("configuration_id")
    @JoinColumn(name = "configuration_id")
    Configuration configuration;

    @ManyToOne
    @MapsId("table_id")
    @JoinColumn(name = "table_id")
    Table table;

    boolean active;

    public ConfigurationTable(){}

    public ConfigurationTable(ConfigurationTableKey id, Configuration configuration, Table table, boolean active) {
        this.id = id;
        this.configuration = configuration;
        this.table = table;
        this.active = active;
    }

    public ConfigurationTableKey getId() {
        return id;
    }

    public void setId(ConfigurationTableKey id) {
        this.id = id;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

