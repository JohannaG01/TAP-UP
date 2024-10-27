package com.johannag.tapup.users.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.UUID;

import static com.johannag.tapup.users.application.exceptions.UserExceptionCode.USER_NOT_FOUND;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(String email) {
        super(String.format("User with email %s was not found", email), HttpStatus.NOT_FOUND,
                USER_NOT_FOUND.toString());
    }

    public UserNotFoundException(UUID uuid) {
        super(String.format("User with uuid %s was not found", uuid), HttpStatus.NOT_FOUND, USER_NOT_FOUND.toString());
    }

    public UserNotFoundException(Collection<UUID> uuids) {
        super(String.format("Users with uuid %s were not found", uuids), HttpStatus.UNPROCESSABLE_ENTITY,
                USER_NOT_FOUND.toString());
    }
}
