package com.unvalued.api.controller;

import com.unvalued.api.model.ApplicationResponse;
import com.unvalued.api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stocks")
public class StockRestController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<ApplicationResponse> getSuggestedStocks() {
        ApplicationResponse applicationResponse = stockService.retrieveSuggestedStocks();
        return new ResponseEntity<>(applicationResponse, applicationResponse.getStatus());
    }
}
