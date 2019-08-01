package com.vironit.mwallet.controller.rest.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
public class TransactionRestControllerException extends Exception {
    public TransactionRestControllerException() {
        super();
    }

    public TransactionRestControllerException(String message) {
        super(message);
    }

    public TransactionRestControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionRestControllerException(Throwable cause) {
        super(cause);
    }
}
