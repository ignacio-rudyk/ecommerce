package com.ignacio.rudyk.generic.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class EcommerceException extends RuntimeException {

    private int code;

    private HttpStatus httpStatus;

    private static final String ECOMMERCE_EXCEPTION_MSG = "Hubo un error en la operacion";

    public EcommerceException(String message, int code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public EcommerceException(String message) {
        this(message, 1, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public EcommerceException() {
        this(ECOMMERCE_EXCEPTION_MSG);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}