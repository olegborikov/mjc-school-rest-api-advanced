package com.epam.esm.exception;

/**
 * {@code ResourceNotFoundException} is generated in case resource {@link com.epam.esm.entity.Tag}
 * or {@link com.epam.esm.entity.GiftCertificate} don't found in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see RuntimeException
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String messageKey;
    private final String messageParameter;

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param messageKey       the message key to get message from properties files
     * @param messageParameter the message parameter to set into message from properties files
     */
    public ResourceNotFoundException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    /**
     * Gets message key.
     *
     * @return the message key
     */
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * Gets message parameter.
     *
     * @return the message parameter
     */
    public String getMessageParameter() {
        return messageParameter;
    }
}
