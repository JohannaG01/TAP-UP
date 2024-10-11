package com.johannag.tapup.globals.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, String code) {
        super(message, HttpStatus.NOT_FOUND, code);
    }
}
