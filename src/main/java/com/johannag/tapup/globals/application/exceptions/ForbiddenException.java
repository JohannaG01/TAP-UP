package com.johannag.tapup.globals.application.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends ApiException {

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message, String code) {
        super(message, HttpStatus.FORBIDDEN, code);
    }
}
