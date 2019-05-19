package com.dpo.centralized_restaurant.Model.Request;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.persistence.*;

/**
 * Handles the action of ordering a request according to the necessary parameters
 */

@Entity
public class RequestOrder {

    private int request_id;
    private int dish_id;
    private int actual_service;
    // Cantidad de platos pedidos de ese tipo de plato
    private int quantity;
    private String activation_date;

    public RequestOrder() {}

    public RequestOrder(int request_id, int dish_id, int actual_service, String activation_date) {
        this.request_id = request_id;
        this.dish_id = dish_id;
        this.actual_service = actual_service;
        this.activation_date = activation_date;
    }

    public RequestOrder(int request_id, int dish_id, int actual_service, int quantity, String activation_date) {
        this.request_id = request_id;
        this.dish_id = dish_id;
        this.actual_service = actual_service;
        this.quantity = quantity;
        this.activation_date = activation_date;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
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
