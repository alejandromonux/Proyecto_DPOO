package com.dpo.centralized_restaurant.Controller;

import com.dpo.centralized_restaurant.Model.Request.RequestDish;
import com.dpo.centralized_restaurant.database.OrderService;

public class CookingThread extends Thread {

    private OrderService orderS;
    private Controller controller;
    private RequestDish requestDish;
    private int idComanda;

    public CookingThread(RequestDish rd, OrderService orderS, int id) {
        this.requestDish = rd;
        this.idComanda = id;
        this.orderS = orderS;
        this.start();
    }

    @Override
    public void run() {
        try {
            if (requestDish.getActualService() < 2) {
                controller.getInstance().getVista().getJpTableOrders().update(orderS.getMyOrders(idComanda), controller.getInstance());
                controller.getInstance().getServerTaula().updateOrders();

                orderS.updateComanda(requestDish,idComanda);
                controller.getInstance().getVista().getJpTableOrders().update(orderS.getMyOrders(idComanda), controller.getInstance());
                controller.getInstance().getServerTaula().updateOrders();

            }

        }catch (Exception e){
        }
    }

}
