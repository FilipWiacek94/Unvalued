package com.unvalued.api.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Data
public class ApplicationResponse {
    private HttpStatus status;
    private Set<Stock> stocks;
    private String message;

}
