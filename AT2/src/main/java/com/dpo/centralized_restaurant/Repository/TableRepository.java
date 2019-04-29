package com.dpo.centralized_restaurant.Repository;


import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<Mesa, String> {

    Optional<Mesa> findOneById(String id);

    @Query("SELECT t FROM Mesa t WHERE t.active = true")
    List<Mesa> findActive();

}
