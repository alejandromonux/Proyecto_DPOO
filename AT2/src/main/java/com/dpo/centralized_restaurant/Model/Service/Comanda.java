package com.dpo.centralized_restaurant.Model.Service;

public class Comanda {
    private long idTable;
    private String dishname;
    private int dishquant;

    public Comanda(long idTable, String dishname, int dishquant) {
        this.idTable = idTable;
        this.dishname = dishname;
        this.dishquant = dishquant;
    }

    public long getIdTable() {
        return idTable;
    }

    public void setIdTable(long idTable) {
        this.idTable = idTable;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public int getDishquant() {
        return dishquant;
    }

    public void setDishquant(int dishquant) {
        this.dishquant = dishquant;
    }
}
