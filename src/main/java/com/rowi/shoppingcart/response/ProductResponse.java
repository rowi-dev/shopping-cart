package com.rowi.shoppingcart.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ProductResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("carton_size")
    private Integer cartonSize;
    @JsonProperty("carton_price")
    private BigDecimal cartonPrice;
    @JsonProperty("variable_cost_ratio")
    private BigDecimal variableCostRatio;
    @JsonProperty("discount_level")
    private Integer discountLevel;
    @JsonProperty("discount")
    private BigDecimal discount;
}
