package com.vironit.mwallet.controller.rest.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
public class WalletRestControllerException extends Exception {
    public WalletRestControllerException() {
        super();
    }

    public WalletRestControllerException(String message) {
        super(message);
    }

    public WalletRestControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletRestControllerException(Throwable cause) {
        super(cause);
    }
}
