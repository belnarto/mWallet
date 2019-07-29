package com.vironit.mwallet.controller.rest.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

@SuppressWarnings("unused")
public class CurrencyValidationErrorException extends CurrencyRestControllerException
        implements ValidationException {

    private List<ObjectError> objectErrors;

    public List<ObjectError> getObjectErrors() {
        return objectErrors;
    }

    public CurrencyValidationErrorException() {
        super();
    }

    public CurrencyValidationErrorException(String message) {
        super(message);
    }

    public CurrencyValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyValidationErrorException(Throwable cause) {
        super(cause);
    }

    public CurrencyValidationErrorException(List<ObjectError> objectErrors) {
        super();
        this.objectErrors = objectErrors;
    }
}
