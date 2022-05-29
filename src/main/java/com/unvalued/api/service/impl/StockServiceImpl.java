package com.unvalued.api.service.impl;

import com.google.common.collect.Lists;
import com.unvalued.api.model.ApplicationResponse;
import com.unvalued.api.model.Stock;
import com.unvalued.api.model.StockApiResponse;
import com.unvalued.api.service.StockService;
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

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${polygon.api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;



    @Override
    public ApplicationResponse retrieveSuggestedStocks() {
        return retrieveStocks();
    }

    private ApplicationResponse retrieveStocks() {
        ResponseEntity<StockApiResponse> entity = restTemplate.getForEntity(
                prepareApiUrl(),
                StockApiResponse.class
        );

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


    private List<Stock> filterStock(List<Stock> stocks) {
        return stocks.stream()
                .filter(s -> s.getStockPrice() <= 10)
                .toList();
    }

    private String prepareApiUrl() {
        return String.format("%s%s?adjusted=true&apiKey=%s", apiUrl, LocalDate.now().minus(Period.ofDays(1)), apiKey);
    }

    private ResponseEntity<StockApiResponse> callApi() {
        return null;
    }
}
