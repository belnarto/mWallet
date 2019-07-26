package com.vironit.mwallet.controller.mvc.exception;

@SuppressWarnings("unused")
public class WalletControllerException extends Exception {
    public WalletControllerException() {
        super();
    }

    public WalletControllerException(String message) {
        super(message);
    }

    public WalletControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletControllerException(Throwable cause) {
        super(cause);
    }
}
