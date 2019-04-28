package com.dpo.centralized_restaurant.Model.Service;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

public class ServiceDish {
    private Comanda comanda;
    private boolean prepared;

    public ServiceDish(Comanda comanda, boolean prepared) {
        this.comanda = comanda;
        this.prepared = prepared;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }
}
