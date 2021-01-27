package com.epam.esm.exception;

/**
 * {@code ResourceNotFoundException} is generated in case entity doesn't found in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see ServiceException
 */
public class ResourceNotFoundException extends ServiceException {

    /**
     * Instantiates a new resource not found exception.
     *
     * @param messageKey       the message key to get message from properties files
     * @param messageParameter the message parameter to set into message from properties files
     */
    public ResourceNotFoundException(String messageKey, String messageParameter) {
        super(messageKey, messageParameter);
    }

    /**
     * Instantiates a new resource not found exception.
     *
     * @param messageKey the message key to get message from properties files
     */
    public ResourceNotFoundException(String messageKey) {
        super(messageKey);
    }
}
