package com.epam.esm.exception;

import lombok.Value;

/**
 * Class {@code ExceptionHandlerResponse} presents entity which will be
 * returned from controller in case generating exception on server.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Value
public class ExceptionHandlerResponse {

    int exceptionCode;
    String exceptionMessage;
}
