package com.johannag.tapup.users.application.exceptions;

import com.johannag.tapup.globals.exceptions.ConflictException;

public class UserAlreadyExistsException extends ConflictException {

    public UserAlreadyExistsException(String email) {
        super(String.format("User with email %s already exists", email));
    }
}
