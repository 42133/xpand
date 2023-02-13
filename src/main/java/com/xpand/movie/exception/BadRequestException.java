package com.xpand.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class BadRequestException extends MovieApiException {

    private static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(String errorMessage) {
        super(status, errorMessage);
    }

    public BadRequestException(String errorMessage, Object... errorArguments) {
        super(status, errorMessage, errorArguments);
    }
}