package com.vironit.mwallet.controller.rest.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

@SuppressWarnings("unused")
public class UserValidationErrorException extends UserRestControllerException
        implements ValidationException {

    private List<ObjectError> objectErrors;

    public List<ObjectError> getObjectErrors() {
        return objectErrors;
    }

    public UserValidationErrorException() {
        super();
    }

    public UserValidationErrorException(String message) {
        super(message);
    }

    public UserValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserValidationErrorException(Throwable cause) {
        super(cause);
    }

    public UserValidationErrorException(List<ObjectError> objectErrors) {
        super();
        this.objectErrors = objectErrors;
    }
}
