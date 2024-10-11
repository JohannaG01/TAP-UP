package com.johannag.tapup.globals.application.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(String message, String code) {
        super(message, HttpStatus.CONFLICT, code);
    }
}
