package com.rowi.shoppingcart.controller;

import com.rowi.shoppingcart.exception.ValueNotExistException;
import com.rowi.shoppingcart.response.ProductRateResponse;
import com.rowi.shoppingcart.response.ProductResponse;
import com.rowi.shoppingcart.service.ProductService;
import com.rowi.shoppingcart.util.AppResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    private static final Integer STATUS_CODE_SUCCESS = 1;
    private static final String STATUS_SUCCESS = "Success";
    private static final Integer STATUS_CODE_ERROR = 0;
    private static final String STATUS_ERROR = "Error";

    @Nested
    @DisplayName("Test getAllProduct Method")
    class getAllProductSuccessMethodTest {
        @Test
        @DisplayName("getAllProductSuccess_Test")
        void getAllProductSuccess_Test() throws Exception {
            List<ProductResponse> productResponseList = loadProducts();
            Mockito.when(productService.getAllProducts()).thenReturn(productResponseList);
            AppResponse<?> response = productController.getAllProduct();
            Assertions.assertEquals(STATUS_CODE_SUCCESS, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_SUCCESS, response.getMeta().getMessage());
            Assertions.assertEquals(ArrayList.class, response.getData().getClass());
        }

        @Test
        @DisplayName("getAllProductError_Test")
        void getAllProductError_Test() throws Exception {
            List<ProductResponse> productResponseList = loadProducts();
            Mockito.when(productService.getAllProducts()).thenThrow(new Exception("Some Error"));
            AppResponse<?> response = productController.getAllProduct();
            Assertions.assertEquals(STATUS_CODE_ERROR, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_ERROR, response.getMeta().getMessage());
            Assertions.assertNotNull(response.getError());
        }

    }

    @Nested
    @DisplayName("Test getProductById Method")
    class getProductByIdMethodTest {
        @Test
        @DisplayName("getProductByIdSuccess_Test")
        void getProductByIdSuccess_Test() throws Exception {
            List<ProductRateResponse> productRateResponses = loadProductRates();
            Mockito.when(productService.getProductPriceById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(productRateResponses);
            AppResponse<?> response = productController.getProductById(Mockito.anyLong(), Mockito.anyLong());
            Assertions.assertEquals(STATUS_CODE_SUCCESS, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_SUCCESS, response.getMeta().getMessage());
            Assertions.assertEquals(ArrayList.class, response.getData().getClass());
        }

        @Test
        @DisplayName("getProductByIdNotFound_Test")
        void getProductByIdNotFound_Test() throws Exception {
            List<ProductResponse> productResponseList = loadProducts();
            String errorMsg = "Product Not found";
            Mockito.when(productService.getProductPriceById(Mockito.anyLong(), Mockito.anyLong()))
                    .thenThrow(new ValueNotExistException(404, errorMsg));
            AppResponse<?> response = productController.getProductById(Mockito.anyLong(), Mockito.anyLong());
            Assertions.assertEquals(STATUS_CODE_ERROR, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_ERROR, response.getMeta().getMessage());
            Assertions.assertNotNull(response.getError());
            Assertions.assertEquals(errorMsg, response.getError().getDetail());
        }

        @Test
        @DisplayName("getProductByIdInternalError_Test")
        void getProductByIdInternalError_Test() throws Exception {
            List<ProductResponse> productResponseList = loadProducts();
            Mockito.when(productService.getProductPriceById(Mockito.anyLong(), Mockito.anyLong()))
                    .thenThrow(Exception.class);
            AppResponse<?> response = productController.getProductById(Mockito.anyLong(), Mockito.anyLong());
            Assertions.assertEquals(STATUS_CODE_ERROR, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_ERROR, response.getMeta().getMessage());
            Assertions.assertNotNull(response.getError());
        }
    }

    @Nested
    @DisplayName("Test calculateProductById Method")
    class calculateProductByIdMethodTest {
        @Test
        @DisplayName("calculateProductByIdSuccess_Test")
        void calculateProductById_Test() throws Exception {
            ProductRateResponse product = ProductRateResponse.builder()
                    .qty(110).unit(10).cartons(5).amount(new BigDecimal(2000)).build();
            Mockito.when(productService.calculateProductPriceById(Mockito.anyLong(), Mockito.anyInt())).thenReturn(product);
            AppResponse<?> response = productController.calculateProductById(Mockito.anyLong(), Mockito.anyInt());
            Assertions.assertEquals(STATUS_CODE_SUCCESS, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_SUCCESS, response.getMeta().getMessage());
            Assertions.assertEquals(ProductRateResponse.class, response.getData().getClass());
        }

        @Test
        @DisplayName("calculateProductByIdNotFound_Test")
        void calculateProductByIdNotFound_Test() throws Exception {
            String errorMsg = "Product Not found";
            Mockito.when(productService.calculateProductPriceById(Mockito.anyLong(), Mockito.anyInt()))
                    .thenThrow(new ValueNotExistException(404, errorMsg));
            AppResponse<?> response = productController.calculateProductById(Mockito.anyLong(), Mockito.anyInt());
            Assertions.assertEquals(STATUS_CODE_ERROR, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_ERROR, response.getMeta().getMessage());
            Assertions.assertNotNull(response.getError());
            Assertions.assertEquals(errorMsg, response.getError().getDetail());
        }

        @Test
        @DisplayName("calculateProductByIdInternalError_Test")
        void calculateProductByIdInternalError_Test() throws Exception {
            Mockito.when(productService.calculateProductPriceById(Mockito.anyLong(), Mockito.anyInt()))
                    .thenThrow(Exception.class);
            AppResponse<?> response = productController.calculateProductById(Mockito.anyLong(), Mockito.anyInt());
            Assertions.assertEquals(STATUS_CODE_ERROR, response.getMeta().getCode());
            Assertions.assertEquals(STATUS_ERROR, response.getMeta().getMessage());
            Assertions.assertNotNull(response.getError());
        }
    }

    private List<ProductResponse> loadProducts() {
        List<ProductResponse> productList = new ArrayList<>();
        ProductResponse product = new ProductResponse(1L,
                "penguine Ears",
                20,
                new BigDecimal(175),
                new BigDecimal(10),
                3,
                new BigDecimal(30));
        ProductResponse product1 = new ProductResponse(2L,
                "penguine Ears",
                5,
                new BigDecimal(825),
                new BigDecimal(10),
                3,
                new BigDecimal(30));
        productList.add(product);
        productList.add(product1);
        return productList;
    }

    private List<ProductRateResponse> loadProductRates() {
        List<ProductRateResponse> productList = new ArrayList<>();
        ProductRateResponse product = ProductRateResponse.builder().qty(110).unit(10).cartons(5).amount(new BigDecimal(2000)).build();
        ProductRateResponse product1 = ProductRateResponse.builder().qty(21).unit(1).cartons(2).amount(new BigDecimal(500)).build();

        productList.add(product);
        productList.add(product1);
        return productList;
    }

}