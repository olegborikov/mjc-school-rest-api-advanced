package com.epam.esm.exception;

/**
 * {@code ResourceNotFoundException} is generated in case entity already exists in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see ServiceException
 */
public class ResourceExistsException extends ServiceException {

    /**
     * Instantiates a new resource exists exception.
     *
     * @param messageKey       the message key to get message from properties files
     * @param messageParameter the message parameter to set into message from properties files
     */
    public ResourceExistsException(String messageKey, String messageParameter) {
        super(messageKey, messageParameter);
    }
}
