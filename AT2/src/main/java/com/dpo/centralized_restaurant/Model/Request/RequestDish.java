package com.dpo.centralized_restaurant.Model.Request;

/**
 * Stores all the required information related to a dish request
 */
public class RequestDish {

    private Long id;
    private String name;
    private double cost;
    private int units;
    private int timecost;
    private int historicOrders;
    private boolean active;
    private int actual_service;
    private String activation_date;

    public RequestDish(Long id, String name, double cost, int units, int timecost, int historicOrders, boolean active, int actual_service, String activation_date) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.units = units;
        this.timecost = timecost;
        this.historicOrders = historicOrders;
        this.active = active;
        this.actual_service = actual_service;
        this.activation_date = activation_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getActual_service() {
        return actual_service;
    }

    public void setActual_service(int actual_service) {
        this.actual_service = actual_service;
    }

    public String getActivation_date() {
        return activation_date;
    }

    public void setActivation_date(String activation_date) {
        this.activation_date = activation_date;
    }
}
