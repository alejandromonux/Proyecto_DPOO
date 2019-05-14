package com.dpo.centralized_restaurant.Model.Graphics;

public class OrderedDish {
    private String dishName;
    private int timesOrdered;

    public OrderedDish(String dishName, int timesOrdered) {
        this.dishName = dishName;
        this.timesOrdered = timesOrdered;
    }

    public static int compare(OrderedDish o1, OrderedDish o2){
        if (o1.timesOrdered > o2.timesOrdered){
            return(o1.timesOrdered);
        }else{
            return(o2.timesOrdered);
        }
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
