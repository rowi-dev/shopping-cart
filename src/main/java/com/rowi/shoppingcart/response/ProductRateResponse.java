package com.rowi.shoppingcart.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRateResponse {
    private int qty;
    private int unit;
    private BigDecimal amount;
    private int cartons;
}
