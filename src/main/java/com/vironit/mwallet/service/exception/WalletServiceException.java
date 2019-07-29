package com.vironit.mwallet.service.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
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
