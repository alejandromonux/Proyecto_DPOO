package model;

import java.io.Serializable;

public class Dish implements Serializable {

    private String name;

    private double cost;

    private int units;

    private int timecost;

    private int historicOrders;

    private boolean active;

    public Dish(){}

    public Dish(String name, double cost, int units, int timecost) {
        this.name = name;
        this.cost = cost;
        this.units = units;
        this.timecost = timecost;
        this.historicOrders = 0;
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

    public int getTimecost() {
        return timecost;
    }

    public void setTimecost(int timecost) {
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
