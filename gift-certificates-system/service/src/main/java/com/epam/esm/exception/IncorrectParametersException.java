package com.epam.esm.exception;

public class IncorrectParametersException extends RuntimeException{
    public IncorrectParametersException() {
    }

    public IncorrectParametersException(String message) {
        super(message);
    }

    public IncorrectParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectParametersException(Throwable cause) {
        super(cause);
    }
}
