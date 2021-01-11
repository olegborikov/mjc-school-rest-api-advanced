package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {// TODO: 11.01.2021 documentation

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorHandler handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorHandler(exception.getMessage(), ErrorCode.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectParameterValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorHandler handleIncorrectParameterValueException(IncorrectParameterValueException exception) {
        return new ErrorHandler(exception.getMessage(), ErrorCode.INCORRECT_PARAMETER_VALUE);
    }
}
