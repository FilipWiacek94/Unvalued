package com.unvalued.api.service;

import com.unvalued.api.model.ApplicationResponse;
import com.unvalued.api.model.Stock;
import com.unvalued.api.model.StockApiResponse;

import java.util.List;

public interface StockService {
    ApplicationResponse retrieveSuggestedStocks();
}
