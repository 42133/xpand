package com.xpand.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends MovieApiException {

    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String errorMessage) {
        super(status, errorMessage);
    }

    public ResourceNotFoundException(String errorMessage, Object... errorArguments) {
        super(status, errorMessage, errorArguments);
    }
}