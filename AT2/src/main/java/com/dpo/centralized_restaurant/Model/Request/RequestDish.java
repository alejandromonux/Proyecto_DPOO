package com.dpo.centralized_restaurant.Model.Request;

import java.io.Serializable;

/**
 * Stores all the required information related to a dish request
 */
public class RequestDish implements Serializable {
    private int id;
    private int request_id;
    private int dish_id;
    private String name;
    private float cost;
    private int units;
    private int timecost;
    private String activation_date;
    private int actualService;

    public RequestDish(){}

    public RequestDish(int id, int dish_id, int request_id, String name, float cost, int units, int timecost, String activation_date, int actualService) {
        this.id = id;
        this.dish_id = dish_id;
        this.request_id = request_id;
        this.name = name;
        this.cost = cost;
        this.units = units;
        this.timecost = timecost;
        this.activation_date = activation_date;
        this.actualService = actualService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getTimecost() {
        return timecost;
    }

    public void setTimecost(int timecost) {
        this.timecost = timecost;
    }

    public String getActivation_date() {
        return activation_date;
    }

    public void setActivation_date(String activation_date) {
        this.activation_date = activation_date;
    }

    public int getActualService() {
        return actualService;
    }

    public void setActualService(int actualService) {
        this.actualService = actualService;
    }
}
