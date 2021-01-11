package com.epam.esm.exception;

public class IncorrectParameterValueException extends RuntimeException { // TODO: 11.01.2021 documentation

    private final String messageKey;
    private final String messageParameter;

    public IncorrectParameterValueException(String messageKey, String messageParameter) {
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
