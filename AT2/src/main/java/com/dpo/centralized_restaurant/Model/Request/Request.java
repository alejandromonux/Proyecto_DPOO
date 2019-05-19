package com.dpo.centralized_restaurant.Model.Request;

import javax.persistence.*;

/**
 * Stores and handles the core data of the requests, such their attributes and configurations
 */

@Entity
public class Request {

    private int id;
    private String name;
    // Para saber cuantas personas hay reservadas, y asi pder adjuntarle una mesa a la reserva
    private int quantity;
    // 0: Esta preparado
    // 1: Esta en mesa
    // 2: Ya esta pagado y es servicio actual
    // 3: Historico
    private int inService;
    private String mesa_name;
    private String password;

    public Request(){}

    public Request(int id, String name, int quantity, int inService, String mesa_name, String password) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.inService = inService;
        this.mesa_name = mesa_name;
        this.password = password;
    }

    public Request(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Request(int id,String name, String password){
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public Request(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int isInService() {
        return inService;
    }

    public void setInService(int inService) {
        this.inService = inService;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            Request other = (Request) obj;
            return id == other.id;
        }
    }

    public String getMesa_name() {
        return mesa_name;
    }

    public void setMesa_name(String mesa_name) {
        this.mesa_name = mesa_name;
    }

    public int getInService() {
        return inService;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
