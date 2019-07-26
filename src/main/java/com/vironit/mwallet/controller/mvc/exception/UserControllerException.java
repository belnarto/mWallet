package com.vironit.mwallet.controller.mvc.exception;

@SuppressWarnings("unused")
public class UserControllerException extends Exception {
    public UserControllerException() {
        super();
    }

    public UserControllerException(String message) {
        super(message);
    }

    public UserControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserControllerException(Throwable cause) {
        super(cause);
    }
}
