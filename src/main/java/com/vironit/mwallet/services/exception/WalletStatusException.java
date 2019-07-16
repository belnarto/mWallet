package com.vironit.mwallet.services.exception;

public class WalletStatusException extends WalletServiceException {

    public WalletStatusException() {
        super();
    }

    public WalletStatusException(String s) {
        super(s);
    }

    public WalletStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletStatusException(Throwable cause) {
        super(cause);
    }

}
