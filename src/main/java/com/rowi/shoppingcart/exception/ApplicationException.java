package com.rowi.shoppingcart.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int status;

    public ApplicationException() {
        super();
    }

    public ApplicationException(int status, String message) {
        super(message);
        this.status = status;
    }

    public ApplicationException(int status, String message, Throwable e) {
        super(message, e);
        this.status = status;
    }

}
