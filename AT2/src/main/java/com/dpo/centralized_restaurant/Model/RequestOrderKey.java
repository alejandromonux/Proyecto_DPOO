package com.dpo.centralized_restaurant.Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
public class RequestOrderKey implements Serializable {

    @Column(name = "request_id")
    Long requestId;

    @Column(name = "dish_id")
    Long dishId;

    public RequestOrderKey(){}

    public RequestOrderKey(Long requestId, Long dishId) {
        this.requestId = requestId;
        this.dishId = dishId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    @Override
    public String toString() {
        return "RequestOrderKey{" +
                "requestId=" + requestId +
                ", dishId=" + dishId +
                '}';
    }
}
