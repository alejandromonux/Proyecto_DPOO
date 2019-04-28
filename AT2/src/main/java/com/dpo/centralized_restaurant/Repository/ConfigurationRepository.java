package com.dpo.centralized_restaurant.Repository;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
import com.dpo.centralized_restaurant.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    @Query("SELECT c FROM Configuration c WHERE c.name = :name AND c.worker = worker")
    Optional<Configuration> findOneByNameAndWorker(@Param("name") String name,@Param("worker") Worker worker);
}
