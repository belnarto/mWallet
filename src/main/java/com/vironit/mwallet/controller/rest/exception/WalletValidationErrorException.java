package com.vironit.mwallet.controller.rest.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

@SuppressWarnings("unused")
public class WalletValidationErrorException extends WalletRestControllerException
        implements ValidationException {

    private List<ObjectError> objectErrors;

    public List<ObjectError> getObjectErrors() {
        return objectErrors;
    }

    public WalletValidationErrorException() {
        super();
    }

    public WalletValidationErrorException(String message) {
        super(message);
    }

    public WalletValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletValidationErrorException(Throwable cause) {
        super(cause);
    }

    public WalletValidationErrorException(List<ObjectError> objectErrors) {
        super();
        this.objectErrors = objectErrors;
    }
}
