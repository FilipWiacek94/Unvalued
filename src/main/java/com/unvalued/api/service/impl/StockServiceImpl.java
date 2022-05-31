package com.unvalued.api.service.impl;

import com.google.common.collect.Lists;
import com.unvalued.api.model.ApplicationResponse;
import com.unvalued.api.model.Stock;
import com.unvalued.api.model.StockApiResponse;
import com.unvalued.api.model.UserPreferences;
import com.unvalued.api.service.StockService;
import com.unvalued.api.service.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserPreferencesService preferencesService;

    @Value("${polygon.api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    @Override
    public ApplicationResponse retrieveSuggestedStocks() {
        return retrieveStocks();
    }

    private ApplicationResponse retrieveStocks() {
        ResponseEntity<StockApiResponse> entity = callApi();

        ApplicationResponse appResponse = new ApplicationResponse();
        appResponse.setStatus(entity.getStatusCode());

        if (entity.getStatusCode() == HttpStatus.OK) {
            List<Stock> stocks = Optional.ofNullable(entity)
                    .map(HttpEntity::getBody)
                    .map(StockApiResponse::getResults)
                    .orElse(Lists.newArrayList());

            appResponse.setStocks(filterStock(stocks));
        } else {
            appResponse.setMessage("Something went wrong. Please try again!");
        }

        return appResponse;
    }

    private Set<Stock> filterStock(List<Stock> stocks) {
        UserPreferences userPreferences = preferencesService.retrieveUserPreferences();

        return stocks.stream()
                .filter(s -> s.getStockPrice() <= userPreferences.getPreferredStockPrice())
                .limit(userPreferences.getStockAmount())
                .collect(Collectors.toSet());
    }

    private String prepareApiUrl(Period lastTradingDate) {
        return String.format("%s%s?adjusted=true&apiKey=%s", apiUrl, LocalDate.now().minus(lastTradingDate), apiKey);
    }

    private ResponseEntity<StockApiResponse> callApi() {
        try {
            ResponseEntity<StockApiResponse> entity = restTemplate.getForEntity(
                    prepareApiUrl(Period.ofDays(1)),
                    StockApiResponse.class
            );

            Integer resultCount = Optional.ofNullable(entity)
                    .map(HttpEntity::getBody)
                    .map(StockApiResponse::getResultsCount)
                    .map(Integer::parseInt)
                    .orElse(0);

            if (resultCount == 0) {
                entity = restTemplate.getForEntity(
                        prepareApiUrl(Period.ofDays(2)),
                        StockApiResponse.class
                );
            }

            return entity;
        } catch (Exception e) {
            return null;
        }
    }
}
