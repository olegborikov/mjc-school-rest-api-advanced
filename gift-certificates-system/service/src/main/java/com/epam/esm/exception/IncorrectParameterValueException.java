package com.epam.esm.exception;

/**
 * {@code IncorrectParameterValueException} is generated in case
 * received parameters have unacceptable value.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see RuntimeException
 */
public class IncorrectParameterValueException extends RuntimeException {

    private final String messageKey;
    private final String messageParameter;

    public IncorrectParameterValueException(String messageKey, String messageParameter) {
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
