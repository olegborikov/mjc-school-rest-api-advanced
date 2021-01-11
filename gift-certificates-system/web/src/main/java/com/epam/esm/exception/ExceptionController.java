package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class ExceptionController {// TODO: 11.01.2021 documentation

    private final ExceptionMessageCreator exceptionMessageCreator;

    @Autowired
    public ExceptionController(ExceptionMessageCreator exceptionMessageCreator) {
        this.exceptionMessageCreator = exceptionMessageCreator;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ExceptionHandlerResponse handleResourceNotFoundException(
            ResourceNotFoundException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(exception.getMessageKey(),
                exception.getMessageParameter(), locale);
        return new ExceptionHandlerResponse(ExceptionCode.RESOURCE_NOT_FOUND, exceptionMessage);
    }

    @ExceptionHandler(value = IncorrectParameterValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ExceptionHandlerResponse handleIncorrectParameterValueException(
            IncorrectParameterValueException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(exception.getMessageKey(),
                exception.getMessageParameter(), locale);
        return new ExceptionHandlerResponse(ExceptionCode.INCORRECT_PARAMETER_VALUE, exceptionMessage);
    }
}
