package com.dpo.centralized_restaurant.Model.Service;

public class Comanda {

    private String tableName;
    private int allDishes;
    private int pendingDishes;
    private int cookingDishes;
    private String date;

    public Comanda(String tableName, int allDishes, int pendingDishes, int cookingDishes, String date) {
        this.tableName = tableName;
        this.allDishes = allDishes;
        this.pendingDishes = pendingDishes;
        this.cookingDishes = cookingDishes;
        this.date = date;
    }

    public String getIdRequest() {
        return tableName;
    }

    public void setIdRequest(String tableName) {
        this.tableName = tableName;
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
