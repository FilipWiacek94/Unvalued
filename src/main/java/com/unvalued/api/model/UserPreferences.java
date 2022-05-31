package com.unvalued.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPreferences {
    private float preferredStockPrice;
    private int stockAmount;
}
