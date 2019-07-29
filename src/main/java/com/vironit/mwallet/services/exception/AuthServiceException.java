package com.vironit.mwallet.services.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
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
