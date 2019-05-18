package com.dpo.centralized_restaurant.Model.ModelDTO;

/**
 * Allows to send messages to Entrada class
 */
public class ClientDTO {
    private String nombreReserva;
    private String password;
    private long idMesa;

    public ClientDTO(){}

    public ClientDTO(String nombreReserva, String password, long idMesa) {
        this.nombreReserva = nombreReserva;
        this.password = password;
        this.idMesa = idMesa;
    }

    public String getNombreReserva() {
        return nombreReserva;
    }

    public void setNombreReserva(String nombreReserva) {
        this.nombreReserva = nombreReserva;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(long idMesa) {
        this.idMesa = idMesa;
    }
}
