package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Class {@code ExceptionMessageCreator} designed to create an exception message.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Component
public class ExceptionMessageCreator {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionMessageCreator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Create exception message by getting string from properties files and set parameter to it.
     *
     * @param exceptionMessageKey       the exception message key to get message from properties files
     * @param exceptionMessageParameter the exception message parameter
     * @param locale                    the locale of exception message
     * @return the created localized exception message
     */
    public String createMessage(String exceptionMessageKey, String exceptionMessageParameter, Locale locale) {
        String message = messageSource.getMessage(exceptionMessageKey, new Object[]{}, locale);
        return String.format(message, exceptionMessageParameter);
    }
}
