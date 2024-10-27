package com.johannag.tapup.users.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ConflictException;

import static com.johannag.tapup.users.application.exceptions.UserExceptionCode.USER_ALREADY_EXISTS;

public class UserAlreadyExistsException extends ConflictException {

    public UserAlreadyExistsException(String email) {
        super(String.format("User with email [%s] already exists", email), USER_ALREADY_EXISTS.toString());
    }
}
