package com.rowi.shoppingcart.util;

public class UriProperties {
    public static final String V1 = "/v1";
    public static final String URI_SHOPPING_CART = "/api" + V1;
    public static final String URI_PRODUCT = "/product";
    public static final String URI_CALCULATOR = "/calculator";
    public static final String GET_PRODUCT_BY_ID = URI_PRODUCT + "/{id}";
    public static final String CALCULATE_BY_ID = URI_PRODUCT + URI_CALCULATOR + "/{id}";
}
