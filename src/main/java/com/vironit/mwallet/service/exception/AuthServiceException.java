package com.vironit.mwallet.service.exception;

@SuppressWarnings("unused")
public class AuthServiceException extends Exception {
    public AuthServiceException() {
        super();
    }

    public AuthServiceException(String message) {
        super(message);
    }

    public AuthServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthServiceException(Throwable cause) {
        super(cause);
    }
}
