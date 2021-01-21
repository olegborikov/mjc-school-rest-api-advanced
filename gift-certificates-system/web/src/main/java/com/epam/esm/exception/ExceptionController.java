package com.epam.esm.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * Class {@code ExceptionController} represents controller which handle
 * all exceptions generated by {@link com.epam.esm.service.GiftCertificateService},
 * {@link com.epam.esm.service.TagService} and all {@link RuntimeException}
 * and {@link HttpMediaTypeNotSupportedException} generated by server.
 *
 * @author Oleg Borikov
 * @since 1.0
 */
@Log4j2
@RestControllerAdvice
public class ExceptionController {

    private final ExceptionMessageCreator exceptionMessageCreator;

    @Autowired
    public ExceptionController(ExceptionMessageCreator exceptionMessageCreator) {
        this.exceptionMessageCreator = exceptionMessageCreator;
    }

    /**
     * Handle {@link ResourceNotFoundException} which generated by
     * {@link com.epam.esm.service.GiftCertificateService}
     * or {@link com.epam.esm.service.TagService}.
     *
     * @param exception the exception
     * @param locale    the locale of HTTP request
     * @return the exception handler
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionHandlerResponse handleResourceNotFoundException(
            ResourceNotFoundException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(exception.getMessageKey(),
                exception.getMessageParameter(), locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(ExceptionCode.RESOURCE_NOT_FOUND, exceptionMessage);
    }

    /**
     * Handle {@link IncorrectParameterValueException} which generated by
     * {@link com.epam.esm.service.GiftCertificateService}
     * or {@link com.epam.esm.service.TagService}.
     *
     * @param exception the exception
     * @param locale    the locale of HTTP request
     * @return the exception handler
     */
    @ExceptionHandler(IncorrectParameterValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionHandlerResponse handleIncorrectParameterValueException(
            IncorrectParameterValueException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(exception.getMessageKey(),
                exception.getMessageParameter(), locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(ExceptionCode.INCORRECT_PARAMETER_VALUE, exceptionMessage);
    }

    @ExceptionHandler(ResourceExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionHandlerResponse handleResourceExistsException(ResourceExistsException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(exception.getMessageKey(),
                exception.getMessageParameter(), locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(ExceptionCode.RESOURCE_EXISTS, exceptionMessage);
    }

    /**
     * Handle {@link RuntimeException} which generated by server.
     *
     * @param exception the exception
     * @param locale    the locale of HTTP request
     * @return the exception handler
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionHandlerResponse handleRuntimeException(RuntimeException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.INTERNAL_ERROR,
                exception.getMessage(), locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(ExceptionCode.INTERNAL_ERROR, exceptionMessage);
    }

    /**
     * Handle {@link HttpMediaTypeNotSupportedException} which generated by server.
     *
     * @param exception the exception
     * @param locale    the locale of HTTP request
     * @return the exception handler
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ExceptionHandlerResponse handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.INCORRECT_MEDIA_TYPE,
                exception.getMessage(), locale);
        log.error(exceptionMessage);
        return new ExceptionHandlerResponse(ExceptionCode.INCORRECT_MEDIA_TYPE, exceptionMessage);
    }
}
