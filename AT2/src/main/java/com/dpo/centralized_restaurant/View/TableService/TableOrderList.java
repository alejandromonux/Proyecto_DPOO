package com.dpo.centralized_restaurant.View.TableService;

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Table;

import java.util.ArrayList;

public class TableOrderList {

    private ArrayList<Table> tables;
    public TableOrderList(ArrayList<Table> tables) {
        this.tables = tables;
    }

    public void registerControllers(Controller c) {
    }
}
