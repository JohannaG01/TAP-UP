package com.johannag.tapup.users.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.NotFoundException;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String email) {
        super(String.format("User with email %s was not found", email));
    }

    public UserNotFoundException(UUID uuid) {
        super(String.format("User with uuid %s was not found", uuid));
    }

}
