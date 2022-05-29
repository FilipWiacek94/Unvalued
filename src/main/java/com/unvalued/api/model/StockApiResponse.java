package com.unvalued.api.model;

import lombok.Data;

import java.util.List;

@Data
public class StockApiResponse {
    private String adjusted;
    private String queryCount;
    private List<Stock> results;
    private String resultsCount;
    private String status;
}
