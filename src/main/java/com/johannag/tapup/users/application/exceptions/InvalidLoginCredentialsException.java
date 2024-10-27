package com.johannag.tapup.users.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.UnauthorizedException;

import static com.johannag.tapup.users.application.exceptions.UserExceptionCode.INVALID_CREDENTIALS;

public class InvalidLoginCredentialsException extends UnauthorizedException {

    public InvalidLoginCredentialsException() {
        super("Invalid username or password", INVALID_CREDENTIALS.toString());
    }
}
