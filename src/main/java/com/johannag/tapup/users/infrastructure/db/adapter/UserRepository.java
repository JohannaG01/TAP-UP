package com.johannag.tapup.users.infrastructure.db.adapter;

import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.dtos.SubtractUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;

import java.util.Optional;
import java.util.UUID;

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
    Optional<UserModel> findMaybeByEmail(String email);

    /**
     * Retrieves a {@link UserModel} based on the provided UUID, if present.
     * This method attempts to find a user by their unique identifier (UUID).
     * If no user is found, it returns an empty {@link Optional}.
     *
     * @param uuid the UUID of the user to search for
     * @return an {@link Optional} containing the {@link UserModel} if found, otherwise empty
     */
    Optional<UserModel> findMaybeByUUID(UUID uuid);

    /**
     * Adds funds to the specified entity.
     * <p>
     * This method processes the addition of funds to an entity using the
     * amount specified in the {@link AddUserFundsToEntityDTO}. It updates the
     * entity's balance and returns the updated {@link UserModel} after the operation
     * is successfully completed.
     * </p>
     *
     * @param dto The {@link AddUserFundsToEntityDTO} containing the amount to be added
     *            to the entity's balance.
     * @return The updated {@link UserModel} after the funds have been added.
     */
    UserModel addFunds(AddUserFundsToEntityDTO dto);

    /**
     * Subtracts funds from a user based on the provided {@link SubtractUserFundsToEntityDTO}.
     *
     * @param dto the {@link SubtractUserFundsToEntityDTO} containing the fund subtraction details.
     * @return the updated {@link UserModel} after subtracting the specified funds.
     */
    UserModel subtractFunds(SubtractUserFundsToEntityDTO dto);

    /**
     * Retrieves the user ID associated with the specified email address.
     *
     * <p>This method searches for a user in the system using their email address
     * and returns the unique identifier of the user if found. If no user is found
     * with the given email, the method will return {@code null}.</p>
     *
     * @param email the email address of the user to search for
     * @return the unique identifier of the user, or {@code null} if no user is found
     */
    Long findUserIdByEmail(String email);
}
