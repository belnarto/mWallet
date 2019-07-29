package com.vironit.mwallet.service.exception;

@SuppressWarnings("unused")
public class LoginAlreadyDefinedException extends UserServiceException {
    public LoginAlreadyDefinedException() {
        super();
    }

    public LoginAlreadyDefinedException(String message) {
        super(message);
    }

    public LoginAlreadyDefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAlreadyDefinedException(Throwable cause) {
        super(cause);
    }
}
