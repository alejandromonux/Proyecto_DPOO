package com.dpo.centralized_restaurant.service;

import com.dpo.centralized_restaurant.Repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates the general configuration of the service
 */
@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;
    private static ConfigurationService ourInstance;

    public static ConfigurationService getInstance() {    // Referencia SINGLETON
        if (ourInstance == null) {
            ourInstance = new ConfigurationService();
        }
        return ourInstance;
    }

    public ConfigurationService(){

    }
}
