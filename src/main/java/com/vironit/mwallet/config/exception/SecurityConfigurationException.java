package com.vironit.mwallet.config.exception;

@SuppressWarnings("unused")
public class SecurityConfigurationException extends Exception {
    public SecurityConfigurationException() {
        super();
    }

    public SecurityConfigurationException(String message) {
        super(message);
    }

    public SecurityConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityConfigurationException(Throwable cause) {
        super(cause);
    }
}
