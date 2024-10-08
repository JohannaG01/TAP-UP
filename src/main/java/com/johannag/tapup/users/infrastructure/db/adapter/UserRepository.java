package com.johannag.tapup.users.infrastructure.db.adapter;

import com.johannag.tapup.users.domain.models.UserModel;

public interface UserRepository {

    /**
     * Checks if a user exists with the specified email.
     *
     * @param email the email to check for existence
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    boolean userExists(String email);

    /**
     * Saves the specified user model to the database.
     *
     * @param userModel the user model to be saved
     */
    void create(UserModel userModel);
}
