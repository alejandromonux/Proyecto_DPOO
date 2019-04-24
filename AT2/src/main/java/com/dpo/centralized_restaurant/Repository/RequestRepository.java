package com.dpo.centralized_restaurant.Repository;

import com.dpo.centralized_restaurant.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {


}
