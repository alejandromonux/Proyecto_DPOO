package com.dpo.centralized_restaurant.Model.Configuration;

/**
 * Gets all the necessary information from the .json file
 */
public class configJson {

    private int Port_BBDD;
    private String IP_BBDD;
    private String NomBBDD;
    private String User;
    private String Password;
    private int Port_Entrada;
    private int Port_Taula;

    public configJson(){


    }

    public int getPort_BBDD() {

        return Port_BBDD;
    }

    public String getIP_BBDD() {

        return IP_BBDD;
    }

    public String getNomBBDD() {

        return NomBBDD;
    }

    public String getUser() {

        return User;
    }

    public String getPassword() {

        return Password;
    }

    public int getPort_Entrada() {

        return Port_Entrada;
    }

    public int getPort_Taula() {

        return Port_Taula;
    }
}
