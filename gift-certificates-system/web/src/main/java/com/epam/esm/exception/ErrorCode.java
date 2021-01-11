package com.epam.esm.exception;

import lombok.experimental.UtilityClass;

/**
 * Class {@code ErrorCode} presents values, which will be set into {@link ErrorHandler}
 * in case generating exceptions.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@UtilityClass
public class ErrorCode {

    /**
     * Error code in case generating {@link com.epam.esm.exception.IncorrectParameterValueException}.
     */
    public final int INCORRECT_PARAMETER_VALUE = 40;
    /**
     * Error code in case generating {@link com.epam.esm.exception.ResourceNotFoundException}.
     */
    public final int RESOURCE_NOT_FOUND = 44;
}
