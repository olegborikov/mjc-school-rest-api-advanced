package com.epam.esm.exception;

/**
 * {@code ResourceNotFoundException} is generated in case resource {@link com.epam.esm.entity.Tag}
 * or {@link com.epam.esm.entity.GiftCertificate} don't found in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see RuntimeException
 */
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException(String messageKey, String messageParameter) {
        super(messageKey, messageParameter);
    }

    public ResourceNotFoundException(String messageKey) {
        super(messageKey);
    }
}
