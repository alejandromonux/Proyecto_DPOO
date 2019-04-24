package com.dpo.centralized_restaurant.Repository;

import com.dpo.centralized_restaurant.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DishRepository  extends JpaRepository<Dish, String> {
    Optional<Dish> findOneByName(String name);

    @Query("SELECT d FROM Dish d WHERE d.active = true")
    List<Dish> findAllByActive();

    List<Dish> findAllByCostGreaterThan(double cost);
}
