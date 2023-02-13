package com.xpand.movie.exception;

import org.springframework.http.HttpStatus;

/**
 * General I18N Exception so that Exception message creates and translates the message
 */
public class MovieApiException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus errorCode;
    private Object[] errorArguments;

    public MovieApiException(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MovieApiException(HttpStatus errorCode, String errorMessage, Object[] errorArguments) {
        this(errorCode, errorMessage);
        this.errorArguments = errorArguments;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public Object[] getErrorArguments() {
        return errorArguments;
    }

}

