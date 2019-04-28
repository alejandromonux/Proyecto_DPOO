package com.dpo.centralized_restaurant.Model.Request;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import javax.persistence.*;

@Entity
public class RequestOrder {

    @EmbeddedId
    private RequestOrderKey id;

    @ManyToOne
    @MapsId("request_id")
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @MapsId("dish_id")
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "current_service")
    private boolean currentService;

    public RequestOrder() {}

    public RequestOrder(RequestOrderKey id, boolean currentService) {
        this.id = id;
        this.currentService = currentService;
    }
}
