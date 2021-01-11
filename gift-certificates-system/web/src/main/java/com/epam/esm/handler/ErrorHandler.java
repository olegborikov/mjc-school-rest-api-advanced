package com.epam.esm.handler;

import lombok.Value;

/**
 * Class {@code ErrorHandler} presents entity, which will be returned from controller
 * in case generating {@link com.epam.esm.exception.ResourceNotFoundException}
 * or {@link com.epam.esm.exception.IncorrectParameterValueException}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Value
public class ErrorHandler {

    String errorMessage;
    int errorCode;
}
