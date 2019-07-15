package com.vironit.mWallet.services.exception;

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
