package com.dpo.centralized_restaurant.Model.Configuration;

import com.dpo.centralized_restaurant.Model.Worker;

import javax.persistence.*;
import java.util.Set;

/**
 * Handles the general configuration set by a worker
 */

@Entity
public class Configuration {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "worker")
    private Worker worker;

    @OneToMany(mappedBy = "configuration")
    Set<ConfigurationTable> tablesIn;

    @OneToMany(mappedBy = "configuration")
    Set<ConfigurationDish> dishesIn;

    public Configuration(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Configuration(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Set<ConfigurationTable> getTablesIn() {
        return tablesIn;
    }

    public void setTablesIn(Set<ConfigurationTable> tablesIn) {
        this.tablesIn = tablesIn;
    }
}
