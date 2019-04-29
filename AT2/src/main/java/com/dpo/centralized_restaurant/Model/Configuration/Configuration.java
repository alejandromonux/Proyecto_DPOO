package com.dpo.centralized_restaurant.Model.Configuration;

import com.dpo.centralized_restaurant.Model.Worker;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Configuration {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @OneToMany(mappedBy = "Configuration")
    Set<ConfigurationTable> tablesIn;


    public Configuration(){}

    public Configuration(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
