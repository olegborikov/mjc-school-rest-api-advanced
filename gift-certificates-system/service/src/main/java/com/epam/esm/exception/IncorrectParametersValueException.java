package com.epam.esm.exception;

public class IncorrectParametersValueException extends RuntimeException{
    public IncorrectParametersValueException() {
    }

    public IncorrectParametersValueException(String message) {
        super(message);
    }

    public IncorrectParametersValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectParametersValueException(Throwable cause) {
        super(cause);
    }
}
