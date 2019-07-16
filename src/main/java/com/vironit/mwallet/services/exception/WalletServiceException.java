package com.vironit.mwallet.services.exception;

public class WalletServiceException extends Exception {
    public WalletServiceException() {
        super();
    }

    public WalletServiceException(String message) {
        super(message);
    }

    public WalletServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletServiceException(Throwable cause) {
        super(cause);
    }
}
