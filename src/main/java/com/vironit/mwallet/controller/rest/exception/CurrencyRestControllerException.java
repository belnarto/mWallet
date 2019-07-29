package com.vironit.mwallet.controller.rest.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
public class CurrencyRestControllerException extends Exception {
    public CurrencyRestControllerException() {
        super();
    }

    public CurrencyRestControllerException(String message) {
        super(message);
    }

    public CurrencyRestControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyRestControllerException(Throwable cause) {
        super(cause);
    }
}
