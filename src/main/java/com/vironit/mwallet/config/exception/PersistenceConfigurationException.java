package com.vironit.mwallet.config.exception;

@SuppressWarnings("unused")
public class PersistenceConfigurationException extends Exception {
    public PersistenceConfigurationException() {
        super();
    }

    public PersistenceConfigurationException(String message) {
        super(message);
    }

    public PersistenceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceConfigurationException(Throwable cause) {
        super(cause);
    }
}
