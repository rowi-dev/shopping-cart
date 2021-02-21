package com.rowi.shoppingcart.service;

import com.rowi.shoppingcart.response.ProductRateResponse;
import com.rowi.shoppingcart.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    List<ProductRateResponse> getProductPriceById(Long id, Long recordCount);

    ProductRateResponse calculateProductPriceById(Long id, int qty);
}
