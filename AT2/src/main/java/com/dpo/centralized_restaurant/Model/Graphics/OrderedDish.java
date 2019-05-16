package com.dpo.centralized_restaurant.Model.Graphics;

public class OrderedDish {
    private String dishName;
    private int timesOrdered;

    public OrderedDish(String dishName, int timesOrdered) {
        this.dishName = dishName;
        this.timesOrdered = timesOrdered;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getTimesOrdered() {
        return timesOrdered;
    }

    public void setTimesOrdered(int timesOrdered) {
        this.timesOrdered = timesOrdered;
    }
}
