package com.dpo.centralized_restaurant.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Table {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private int chairs;

    @Column(name = "in_use")
    private boolean inUse;

    // Whether the table is available.
    private boolean active;

    @OneToMany(mappedBy = "table")
    private List<Request> requests;

    @OneToMany(mappedBy = "table")
    Set<ConfigurationTable> configurations;

    public Table(){}

    public Table(long id, int chairs) {
        this.id = id;
        this.chairs = chairs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
            Table other = (Table)obj;
            return id == other.id;
        }
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", chairs=" + chairs +
                ", inUse=" + inUse +
                '}';
    }
}
