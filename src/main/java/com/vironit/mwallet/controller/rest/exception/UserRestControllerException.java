package com.vironit.mwallet.controller.rest.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
public class UserRestControllerException extends Exception {
    public UserRestControllerException() {
        super();
    }

    public UserRestControllerException(String message) {
        super(message);
    }

    public UserRestControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRestControllerException(Throwable cause) {
        super(cause);
    }
}
