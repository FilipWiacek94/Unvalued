package com.unvalued.api.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApplicationResponse {
    private HttpStatus status;
    private List<Stock> stocks;
    private String message;

}
