package com.dpo.centralized_restaurant.Repository;

import com.dpo.centralized_restaurant.model.Dish;
import com.dpo.centralized_restaurant.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, String> {

    Optional<Table> findOneById(String id);

    @Query("SELECT t FROM Table t WHERE t.active = true")
    List<Table> findActive();

}
