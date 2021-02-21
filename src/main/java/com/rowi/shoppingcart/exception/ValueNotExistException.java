package com.rowi.shoppingcart.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ValueNotExistException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public ValueNotExistException() {
        super();
    }

    public ValueNotExistException(int status, String message) {
        super(status, message);
    }

    public ValueNotExistException(int status, String message, Throwable e) {
        super(status, message, e);
    }

}
