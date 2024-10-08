package com.johannag.tapup.globals.application.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message, String code) {
        super(message, HttpStatus.BAD_REQUEST, code);
    }

}
