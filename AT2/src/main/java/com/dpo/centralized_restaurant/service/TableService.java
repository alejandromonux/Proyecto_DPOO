package com.dpo.centralized_restaurant.service;

import com.dpo.centralized_restaurant.Repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates the general configuration of the tables
 */

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;
    private static TableService ourInstance;

    public static TableService getInstance() {    // Referencia SINGLETON
        if (ourInstance == null) {
            ourInstance = new TableService();
        }
        return ourInstance;
    }

    public TableService(){

    }
}
