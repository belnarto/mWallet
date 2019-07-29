package com.vironit.mwallet.service.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
public class UserServiceException extends Exception {
    public UserServiceException() {
        super();
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}
