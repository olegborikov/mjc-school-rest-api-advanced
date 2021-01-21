package com.epam.esm.exception;

public class IncorrectParameterValueException extends ServiceException {

    public IncorrectParameterValueException(String messageKey, String messageParameter) {
        super(messageKey, messageParameter);
    }
}
