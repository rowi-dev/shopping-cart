package com.rowi.shoppingcart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppResponse<Object> handleInvalidInputException(RuntimeException ex) {
        logger.error("Invalid Input Exception: {}", ex.getMessage());
        return AppResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
