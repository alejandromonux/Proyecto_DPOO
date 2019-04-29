package com.dpo.centralized_restaurant.service;

import com.dpo.centralized_restaurant.Repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    private static RequestService ourInstance;

    public static RequestService getInstance() {    // Referencia SINGLETON
        if (ourInstance == null) {
            ourInstance = new RequestService();
        }
        return ourInstance;
    }

    public RequestService(){

    }
}
