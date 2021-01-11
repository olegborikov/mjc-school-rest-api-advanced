package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ExceptionMessageCreator {// TODO: 11.01.2021 documentation

    private final MessageSource messageSource;

    @Autowired
    public ExceptionMessageCreator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String createMessage(String exceptionMessageCode, String exceptionMessageParameter, Locale locale) {
        String message = messageSource.getMessage(exceptionMessageCode, new Object[]{}, locale);
        return String.format(message, exceptionMessageParameter);
    }
}
