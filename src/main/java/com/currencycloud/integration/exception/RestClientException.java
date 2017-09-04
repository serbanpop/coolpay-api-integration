package com.currencycloud.integration.exception;

public class RestClientException extends RuntimeException {

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
