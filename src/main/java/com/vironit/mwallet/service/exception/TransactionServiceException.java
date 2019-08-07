package com.vironit.mwallet.service.exception;

@SuppressWarnings({"unused", "WeakerAccess"})
public class TransactionServiceException extends Exception {
    public TransactionServiceException() {
        super();
    }

    public TransactionServiceException(String message) {
        super(message);
    }

    public TransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionServiceException(Throwable cause) {
        super(cause);
    }
}
