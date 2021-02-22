package com.rowi.shoppingcart.repository;

import com.rowi.shoppingcart.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;


@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    @Sql("/query-import.sql")
    @DisplayName("Data JPA test findAll()")
    void getAllProductsTest() {
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(2, productList.size());
    }
}