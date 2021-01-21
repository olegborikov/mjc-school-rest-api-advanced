package com.epam.esm.exception;

public class ResourceExistsException extends RuntimeException{

    private final String messageKey;
    private final String messageParameter;

    public ResourceExistsException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getMessageParameter() {
        return messageParameter;
    }
}
