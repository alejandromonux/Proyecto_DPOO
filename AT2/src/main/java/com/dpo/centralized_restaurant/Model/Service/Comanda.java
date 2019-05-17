package com.dpo.centralized_restaurant.Model.Service;

public class Comanda {

    private int idTable;
    private int allDishes;
    private int pendingDishes;
    private int cookingDishes;
    private String date;

    public Comanda(int idTable, int allDishes, int pendingDishes, int cookingDishes, String date) {
        this.idTable = idTable;
        this.allDishes = allDishes;
        this.pendingDishes = pendingDishes;
        this.cookingDishes = cookingDishes;
        this.date = date;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getAllDishes() {
        return allDishes;
    }

    public void setAllDishes(int allDishes) {
        this.allDishes = allDishes;
    }

    public int getPendingDishes() {
        return pendingDishes;
    }

    public void setPendingDishes(int pendingDishes) {
        this.pendingDishes = pendingDishes;
    }

    public int getCookingDishes() {
        return cookingDishes;
    }

    public void setCookingDishes(int cookingDishes) {
        this.cookingDishes = cookingDishes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
