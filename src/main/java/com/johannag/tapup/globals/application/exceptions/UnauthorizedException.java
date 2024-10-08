package com.johannag.tapup.globals.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, String code) {
        super(message, HttpStatus.UNAUTHORIZED, code);
    }
}
