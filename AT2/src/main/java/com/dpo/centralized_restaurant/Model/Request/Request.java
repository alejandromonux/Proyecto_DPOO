package com.dpo.centralized_restaurant.Model.Request;

import com.dpo.centralized_restaurant.Model.Preservice.Mesa;

import javax.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    // Para saber cuantas personas hay reservadas, y asi pder adjuntarle una mesa a la reserva
    @Column(nullable = false)
    private int quantity;

    // 0: Esta preparado
    // 1: Esta en mesa
    // 2: Ya esta pagado y es servicio actual
    // 3: Historico
    @Column(name = "in_service")
    private int inService;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Mesa mesa;

    public Request(){}

    public Request(String name, int quantity) {
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


    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
