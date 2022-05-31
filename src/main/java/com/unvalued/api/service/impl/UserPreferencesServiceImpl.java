package com.unvalued.api.service.impl;

import com.unvalued.api.model.UserPreferences;
import com.unvalued.api.service.UserPreferencesService;
import org.springframework.stereotype.Service;

@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {

    @Override
    public UserPreferences retrieveUserPreferences() {
        return provideTestPreferences();
    }

    private UserPreferences provideTestPreferences() {
        return UserPreferences.builder()
                .stockAmount(10)
                .preferredStockPrice(10)
                .build();
    }
}
