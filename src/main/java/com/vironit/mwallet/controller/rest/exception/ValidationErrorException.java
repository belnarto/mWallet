package com.vironit.mwallet.controller.rest.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

@SuppressWarnings("unused")
public class ValidationErrorException extends UserRestControllerException {

    private List<ObjectError> objectErrors;

    public List<ObjectError> getObjectErrors() {
        return objectErrors;
    }

    public ValidationErrorException() {
        super();
    }

    public ValidationErrorException(String message) {
        super(message);
    }

    public ValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationErrorException(Throwable cause) {
        super(cause);
    }

    public ValidationErrorException(List<ObjectError> objectErrors) {
        super();
        this.objectErrors = objectErrors;
    }
}
