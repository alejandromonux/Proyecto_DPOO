package com.dpo.centralized_restaurant.Controller;

import com.dpo.centralized_restaurant.Model.Request.RequestDish;
import com.dpo.centralized_restaurant.database.OrderService;

public class CookingThread extends Thread {

    private Controller controller;
    private RequestDish requestDish;
    private int idComanda;

    public CookingThread(RequestDish rd, int id) {
        this.requestDish = rd;
        this.idComanda = id;
        this.start();
    }

    @Override
    public void run() {
        try {
            if (requestDish.getActualService() < 2) {
                sleep(1000);
            }
            controller.updateCookedDish(requestDish, idComanda);
        }catch (Exception e){
        }
    }

}
