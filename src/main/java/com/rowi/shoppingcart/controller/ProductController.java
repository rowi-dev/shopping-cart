package com.rowi.shoppingcart.controller;

import com.rowi.shoppingcart.exception.ValueNotExistException;
import com.rowi.shoppingcart.response.ProductRateResponse;
import com.rowi.shoppingcart.response.ProductResponse;
import com.rowi.shoppingcart.service.ProductService;
import com.rowi.shoppingcart.util.AppResponse;
import com.rowi.shoppingcart.util.UriProperties;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(UriProperties.URI_SHOPPING_CART)
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(UriProperties.URI_PRODUCT)
    @ApiOperation(value = "Get all products", nickname = "getAllProducts_1")
    @ApiResponses({@ApiResponse(code = 202, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public AppResponse<List<ProductResponse>> getAllProduct() {
        return AppResponse.okList(productService.getAllProducts());
    }

    @GetMapping(UriProperties.GET_PRODUCT_BY_ID)
    @ApiOperation(value = "Get product price By Id", nickname = "getProductPriceById_2")
    @ApiResponses({@ApiResponse(code = 202, message = "Successful"),
            @ApiResponse(code = 400, message = "Get Product By ID Failed")})
    public AppResponse<List<ProductRateResponse>> getProductById(
            @PathVariable("id") Long id,
            @RequestParam(value = "recordCount", required = false, defaultValue = "50") @Min(1) Long recordCount
    ) {
        try {
            return AppResponse.ok(productService.getProductPriceById(id, recordCount));
        } catch (ValueNotExistException e) {
            return AppResponse.error(e.getStatus(), e.getMessage());
        }
    }

    @GetMapping(UriProperties.CALCULATE_BY_ID)
    @ApiOperation(value = "Calculate product By Id", nickname = "calculateProductPriceById_2")
    @ApiResponses({@ApiResponse(code = 202, message = "Successful"),
            @ApiResponse(code = 400, message = "Calculate price By Product ID Failed")})
    public AppResponse<ProductRateResponse> calculateProductById(
            @PathVariable("id") Long id,
            @RequestParam(value = "qty", required = true) @Min(1) int qty
    ) {
        try {
            return AppResponse.ok(productService.calculateProductPriceById(id, qty));
        } catch (ValueNotExistException e) {
            return AppResponse.error(e.getStatus(), e.getMessage());
        }
    }

}
