package com.dpo.centralized_restaurant.Model.Configuration;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConfigurationDishKey {

    @Column(name = "configuration_id")
    Long configurationId;

    @Column(name = "dish_id")
    Long dishId;

    public ConfigurationDishKey(){}

    public ConfigurationDishKey(Long configurationId, Long dishId) {
        this.configurationId = configurationId;
        this.dishId = dishId;
    }

    public Long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Long configurationId) {
        this.configurationId = configurationId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    @Override
    public String toString() {
        return "ConfigurationDishKey{" +
                "configurationId=" + configurationId +
                ", dishId=" + dishId +
                '}';
    }
}
