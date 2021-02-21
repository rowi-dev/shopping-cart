package com.rowi.shoppingcart.service.impl;

import com.rowi.shoppingcart.ShoppingCartApplication;
import com.rowi.shoppingcart.exception.ValueNotExistException;
import com.rowi.shoppingcart.model.Product;
import com.rowi.shoppingcart.repository.ProductRepository;
import com.rowi.shoppingcart.response.ProductRateResponse;
import com.rowi.shoppingcart.response.ProductResponse;
import com.rowi.shoppingcart.service.ProductService;
import com.rowi.shoppingcart.util.ApplicationErrorCodes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = ShoppingCartApplication.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductService productService = new ProductServiceImpl();

    @Mock
    ProductRepository productRepository;

    @Mock
    private MessageSource messageSource;

    @Test
    @Sql(scripts = {"/query.sql", "/query-import.sql"})
    void getAllProductsTest() {
        List<ProductResponse> productResponses = productService.getAllProducts();
        Assertions.assertEquals(0, productResponses.size());
        productResponses.forEach(productResponse -> {
            Assertions.assertNotNull(productResponse.getCartonPrice());
            Assertions.assertNotNull(productResponse.getCartonSize());
        });
    }

    @Test
    void getProductPriceById() {
        Long id = 1L;
        Long count = 40L;
        Product product = getProduct(id);
        Optional<Product> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById(id)).thenReturn(optionalProduct);
        List<ProductRateResponse> productRateResponses = productService.getProductPriceById(id, count);
        Assertions.assertEquals(count, productRateResponses.size());

        Assertions.assertEquals(new BigDecimal(11).setScale(2, BigDecimal.ROUND_HALF_UP), productRateResponses.get(0).getAmount());
        Assertions.assertEquals(1, productRateResponses.get(0).getUnit());
        Assertions.assertEquals(0, productRateResponses.get(0).getCartons());

        Assertions.assertEquals(new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP), productRateResponses.get(9).getAmount());
        Assertions.assertEquals(0, productRateResponses.get(9).getUnit());
        Assertions.assertEquals(1, productRateResponses.get(9).getCartons());

    }

    @Test
    @DisplayName("Test should pass when method throws the ValueNotExistException Exception")
    void getProductPriceByIdExceptionTestTest() {
        Optional<Product> optionalProduct = Optional.empty();
        Mockito.when(productRepository.findById(1L)).thenReturn(optionalProduct);
        Mockito.when(messageSource.getMessage(String.valueOf(ApplicationErrorCodes.PRODUCT_NOT_FOUND),
                new Object[]{}, LocaleContextHolder.getLocale())).thenReturn("Not found");
        Assertions.assertThrows(ValueNotExistException.class, () -> {
            productService.calculateProductPriceById(1L, 20);
        });
    }


    @Test
    @DisplayName("Test should pass when method throws the ValueNotExistException Exception")
    void calculateProductPriceByIdExceptionTestTest() {
        Optional<Product> optionalProduct = Optional.empty();
        Mockito.when(productRepository.findById(1L)).thenReturn(optionalProduct);
        Mockito.when(messageSource.getMessage(String.valueOf(ApplicationErrorCodes.PRODUCT_NOT_FOUND),
                new Object[]{}, LocaleContextHolder.getLocale())).thenReturn("Not found");
        Assertions.assertThrows(ValueNotExistException.class, () -> {
            productService.calculateProductPriceById(1L, 2);
        });
    }


    @ParameterizedTest
    @MethodSource(value = "calculatorTestSource")
    @DisplayName("Test should pass when method return the expected val and not Exception")
    void calculateProductPriceByIdTest(Long id, int qty, int unit, int carton, Product product, BigDecimal expected) {

        Optional<Product> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById(id)).thenReturn(optionalProduct);
        ProductRateResponse productRateResponse = productService.calculateProductPriceById(id, qty);
        Assertions.assertEquals(expected, productRateResponse.getAmount());
        Assertions.assertEquals(unit, productRateResponse.getUnit());
        Assertions.assertEquals(carton, productRateResponse.getCartons());
    }

    private static Stream<Arguments> calculatorTestSource() {
        Long id = 1L;
        Product product = getProduct(id);
        return Stream.of(
                Arguments.arguments(id, 1, 1, 0, product, new BigDecimal(11).setScale(2, BigDecimal.ROUND_HALF_UP)),
                Arguments.arguments(id, 10, 0, 1, product, new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP)),
                Arguments.arguments(id, 12, 2, 1, product, new BigDecimal(122).setScale(2, BigDecimal.ROUND_HALF_UP)),
                Arguments.arguments(id, 30, 0, 3, product, new BigDecimal(210).setScale(2, BigDecimal.ROUND_HALF_UP))
        );
    }

    private static Product getProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setCartonPrice(new BigDecimal(100));
        product.setCartonSize(10);
        product.setName("Test Product");
        product.setDiscount(new BigDecimal(30));
        product.setDiscountLevel(3);
        product.setVariableCostRatio(new BigDecimal(10));
        return product;
    }

}