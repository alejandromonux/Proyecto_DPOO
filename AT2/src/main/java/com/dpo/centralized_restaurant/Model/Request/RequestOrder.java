package com.dpo.centralized_restaurant.Model.Request;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.persistence.*;

/**
 * Handles the action of ordering a request according to the necessary parameters
 */

@Entity
public class RequestOrder {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Request request;

    @ManyToOne
    private Dish dish;

    @Column(name = "current_service")
    private boolean currentService;

    public RequestOrder() {}


}
