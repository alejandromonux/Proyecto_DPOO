package com.dpo.centralized_restaurant.Model.Preservice;
import com.dpo.centralized_restaurant.Model.Configuration.ConfigurationTable;
import com.dpo.centralized_restaurant.Model.Request.Request;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Mesa {

    @Id
    @Column(nullable = false, name = "name")
    @GeneratedValue
    private String id;

    @Column(nullable = false)
    private int chairs;

    // La mesa esta ocupada por comensales
    @Column(name = "in_use")
    private boolean inUse;

    // Whether the mesa is available.
    private boolean active;

    @OneToMany(mappedBy = "mesa")
    private List<Request> requests;

    @OneToMany(mappedBy = "mesa")
    Set<ConfigurationTable> configurations;

    public Mesa(){}

    public Mesa(String id, int chairs) {
        this.id = id;
        this.chairs = chairs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChairs() {
        return chairs;
    }

    public void setChairs(int chairs) {
        this.chairs = chairs;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            Mesa other = (Mesa)obj;
            return id == other.id;
        }
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", chairs=" + chairs +
                ", inUse=" + inUse +
                '}';
    }
}
