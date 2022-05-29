package com.unvalued.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stock {
    @JsonProperty("T")
    private String stockTag;
    @JsonProperty("c")
    private float stockPrice;
}
