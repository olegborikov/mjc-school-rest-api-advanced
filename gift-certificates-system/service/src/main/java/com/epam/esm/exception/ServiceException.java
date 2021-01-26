package com.epam.esm.exception;

public class ServiceException extends RuntimeException {

    private final String messageKey;
    private final String messageParameter;

    public ServiceException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    public ServiceException(String messageKey) {
        this.messageKey = messageKey;
        this.messageParameter = null;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getMessageParameter() {
        return messageParameter;
    }
}
