package com.vironit.mwallet.controller.rest.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

@SuppressWarnings("unused")
public class TransactionValidationErrorException extends TransactionRestControllerException
        implements ValidationException {

    private List<ObjectError> objectErrors;

    public List<ObjectError> getObjectErrors() {
        return objectErrors;
    }

    public TransactionValidationErrorException() {
        super();
    }

    public TransactionValidationErrorException(String message) {
        super(message);
    }

    public TransactionValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionValidationErrorException(Throwable cause) {
        super(cause);
    }

    public TransactionValidationErrorException(List<ObjectError> objectErrors) {
        super();
        this.objectErrors = objectErrors;
    }
}
