package com.dpo.centralized_restaurant.Repository;

import com.dpo.centralized_restaurant.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findOneByUsername(String username);

    Optional<Worker> findOneByEmailIgnoreCase(String email);

    Optional<Worker> findOneByUsernameAndPassword(String username, String password);

    Optional<Worker> findOneByEmailAndPassword(String email, String password);


}
