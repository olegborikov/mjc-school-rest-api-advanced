package com.epam.esm.exception;

public class ResourceExistsException extends ServiceException {

    public ResourceExistsException(String messageKey, String messageParameter) {
        super(messageKey, messageParameter);
    }
}
