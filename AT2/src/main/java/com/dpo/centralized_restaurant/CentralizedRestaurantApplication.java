package com.dpo.centralized_restaurant;

import com.dpo.centralized_restaurant.service.DishService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main void to be run once the application starts
 */
@SpringBootApplication
public class CentralizedRestaurantApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context= SpringApplication.run(CentralizedRestaurantApplication.class, args);

        DishService dishService = context.getBean(DishService.class);

        dishService.testDishes();

    }

}
