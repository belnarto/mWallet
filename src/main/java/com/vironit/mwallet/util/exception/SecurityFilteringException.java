package com.vironit.mwallet.util.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("unused")
public class SecurityFilteringException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public SecurityFilteringException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}