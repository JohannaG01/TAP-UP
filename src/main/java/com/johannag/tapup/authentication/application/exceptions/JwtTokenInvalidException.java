package com.johannag.tapup.authentication.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.UnauthorizedException;

public class JwtTokenInvalidException extends UnauthorizedException {

    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
