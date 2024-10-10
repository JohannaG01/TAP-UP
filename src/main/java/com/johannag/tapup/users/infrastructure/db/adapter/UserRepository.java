package com.johannag.tapup.users.infrastructure.db.adapter;

import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;

import java.util.Optional;

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
     * @param dto the CreateUserEntityDTO to be saved
     */
    UserModel create(CreateUserEntityDTO dto);

    /**
     * Retrieves an {@link Optional} containing a {@link UserModel} based on the provided email address.
     *
     * @param email The email address used to look up the user.
     * @return An {@link Optional} containing the {@link UserModel} if a user is found, or an empty {@link Optional}
     * if no user exists with the given email.
     */
    Optional<UserModel> findByEmail(String email);
}
