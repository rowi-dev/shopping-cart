package com.rowi.shoppingcart.service.impl;

import com.rowi.shoppingcart.exception.ValueNotExistException;
import com.rowi.shoppingcart.model.Product;
import com.rowi.shoppingcart.repository.ProductRepository;
import com.rowi.shoppingcart.response.ProductRateResponse;
import com.rowi.shoppingcart.response.ProductResponse;
import com.rowi.shoppingcart.service.ProductService;
import com.rowi.shoppingcart.util.ApplicationErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private MessageSource messageSource;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<ProductResponse> getAllProducts() {
        logger.info("Start executing getAllProducts");
        List<Product> products = productRepository.findAll();
        logger.info(products.size() + " products fetched from DB");
        List<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product -> {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .build();
            productResponses.add(productResponse);
        });
        logger.info("End executing getAllProducts");
        return productResponses;
    }

    @Override
    public List<ProductRateResponse> getProductPriceById(Long id, Long recordCount) {
        logger.info("Start executing getProductPriceById with ID: " + id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        List<ProductRateResponse> productResponses = new ArrayList<>();
        if (optionalProduct.isPresent()) {
            logger.info("Product details found");
            Product product = optionalProduct.get();
            for (int i = 1; i <= recordCount; i++) {
                ProductRateResponse productRateResponse = calculateAmount(i, product);
                productResponses.add(productRateResponse);
            }
            return productResponses;
        } else {
            logger.info("Product not found");
            throw new ValueNotExistException(ApplicationErrorCodes.PRODUCT_NOT_FOUND,
                    messageSource.getMessage(String.valueOf(ApplicationErrorCodes.PRODUCT_NOT_FOUND),
                            new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public ProductRateResponse calculateProductPriceById(Long id, int qty) {
        logger.info("Start executing getProductPriceById with ID: " + id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        List<ProductRateResponse> productResponses = new ArrayList<>();
        if (optionalProduct.isPresent()) {
            logger.info("Product details found");
            Product product = optionalProduct.get();
            return calculateAmount(qty, product);
        } else {
            logger.info("Product not found");
            throw new ValueNotExistException(ApplicationErrorCodes.PRODUCT_NOT_FOUND,
                    messageSource.getMessage(String.valueOf(ApplicationErrorCodes.PRODUCT_NOT_FOUND),
                            new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    private ProductRateResponse calculateAmount(int i, Product product) {
        int cartonCount = i / product.getCartonSize();
        int retailCount = i % product.getCartonSize();
        BigDecimal retailPrice = product.getCartonPrice().divide(
                new BigDecimal(product.getCartonSize())).multiply(new BigDecimal(retailCount))
                .multiply(product.getVariableCostRatio().add(new BigDecimal(100))).divide(new BigDecimal(100));

        BigDecimal cartonAmount = product.getCartonPrice().multiply(new BigDecimal(cartonCount));
        if (cartonCount >= product.getDiscountLevel()) {
            cartonAmount = cartonAmount.multiply(new BigDecimal(100).subtract(product.getDiscount()))
                    .divide(new BigDecimal(100));
        }
        return ProductRateResponse.builder()
                .unit(retailCount)
                .qty(i)
                .amount((cartonAmount.add(retailPrice)).setScale(2, BigDecimal.ROUND_HALF_UP))
                .cartons(cartonCount).build();

    }


}
