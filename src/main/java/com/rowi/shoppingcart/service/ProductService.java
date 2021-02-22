package com.rowi.shoppingcart.service;

import com.rowi.shoppingcart.response.ProductRateResponse;
import com.rowi.shoppingcart.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts() throws Exception;

    List<ProductRateResponse> getProductPriceById(Long id, Long recordCount) throws Exception;

    ProductRateResponse calculateProductPriceById(Long id, int qty) throws Exception;
}
