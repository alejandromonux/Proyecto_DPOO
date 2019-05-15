package com.dpo.centralized_restaurant.service;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates the general configuration of the dishes
 */
@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;
    private static DishService ourInstance;

    public static DishService getInstance() {    // Referencia SINGLETON
        if (ourInstance == null) {
            ourInstance = new DishService();
        }
        return ourInstance;
    }

    public DishService(){

    }

    /**
     * Create a group of dishes to start with
     */
    public void testDishes() {

        Dish dish1 = new Dish("Fideua", 4.4,10,10);

        Dish dish2 = new Dish("Paella", 5.4,10,14);

        Dish dish3 = new Dish("Truita de patates", 2.4,10,7);

        Dish dish4 = new Dish("Llom amb Patates", 1.4,5,5);

        Dish dish5 = new Dish("Patata i bejoca", 1.0,15,3);

        Dish dish6 = new Dish("Sopa de galets", 1.4,9,4);

        Dish dish7 = new Dish("Salmo", 3.4,5,7);

        Dish dish8 = new Dish("Amanida amb embotit", 2.4,20,5);

        Dish dish9 = new Dish("Llenguado", 4.4,12,11);

        dishRepository.save(dish1);
        dishRepository.save(dish2);
        dishRepository.save(dish3);
        dishRepository.save(dish4);
        dishRepository.save(dish5);
        dishRepository.save(dish6);
        dishRepository.save(dish7);
        dishRepository.save(dish8);
        dishRepository.save(dish9);

        System.out.println("Top 5 expensive dishes:");
        System.out.println(dishRepository.findAllByCostGreaterThan(5.0));


    }
}
