package com.dpo.centralized_restaurant.repository;

import com.dpo.centralized_restaurant.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {


}
