package com.johannag.tapup.users.application.services;

import com.johannag.tapup.users.application.dtos.*;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Creates a new user based on the provided {@link CreateUserDTO}.
     * <p>
     * This method takes a Data Transfer Object (DTO) containing user information
     * and maps it to a domain model. It performs necessary validations and
     * transformations before saving the user in the system.
     *
     * @param dto the Data Transfer Object containing the user's information,
     *            including email, name, last name, and password.
     * @return a {@link UserModel} representing the newly created user.
     * @throws UserAlreadyExistsException if the provided user already exists in the system.
     **/

    UserModel signIn(CreateUserDTO dto) throws UserAlreadyExistsException;

    /**
     * Authenticates a user using the provided login credentials and returns a {@link UserWithAuthTokenModel}.
     *
     * @param dto The {@link LogInUserDTO} object containing the user's login credentials.
     * @return A {@link UserWithAuthTokenModel} object that contains the authenticated user information and a generated
     * token.
     * @throws InvalidLoginCredentialsException if the login credentials are invalid, such as incorrect username or
     *                                          password.
     */
    UserWithAuthTokenModel logIn(LogInUserDTO dto) throws InvalidLoginCredentialsException;

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to be retrieved. It must be a valid email format.
     * @return the UserModel associated with the given email address.
     * @throws UserNotFoundException if no user is found with the specified email address.
     */
    UserModel findByEmail(String email) throws UserNotFoundException;

    /**
     * Finds a {@link UserModel} by its UUID.
     *
     * @param uuid the UUID of the user to find
     * @return the {@link UserModel} associated with the given UUID
     * @throws UserNotFoundException if no user is found with the provided UUID
     */
    UserModel findOneByUuid(UUID uuid) throws UserNotFoundException;

    /**
     * Adds funds to a user account.
     * <p>
     * This method updates the user's balance by adding the specified amount
     * from the provided {@link AddUserFundsDTO}. It retrieves the user based
     * on the information contained in the DTO and throws a {@link UserNotFoundException}
     * if the user does not exist.
     *
     * @param dto the data transfer object containing the user and amount information
     * @return the updated {@link UserModel} instance after funds have been added
     * @throws UserNotFoundException if the user cannot be found in the system
     */
    UserModel addFunds(AddUserFundsDTO dto) throws UserNotFoundException;

    /**
     * Adds funds to the users specified in the provided list of {@link AddUserFundsDTO}.
     *
     * @param dtos the list of {@link AddUserFundsDTO} containing user UUIDs and corresponding amounts to be added
     * @return a list of {@link UserModel} representing the users whose funds were successfully updated
     * @throws UserNotFoundException if any user in the provided list does not exist
     */
    List<UserModel> addFunds(List<AddUserFundsDTO> dtos) throws UserNotFoundException;

    /**
     * Subtracts funds from a user account based on the provided {@link SubtractUserFundsDTO}.
     *
     * @param dto the {@link SubtractUserFundsDTO} containing the details of the funds to be subtracted.
     * @return the updated {@link UserModel} after funds have been subtracted.
     * @throws UserNotFoundException if the user associated with the provided details is not found.
     */
    UserModel subtractFunds(SubtractUserFundsDTO dto) throws UserNotFoundException;

    /**
     * Validates the existence of users identified by their unique UUIDs.
     *
     * <p>This method checks whether the users corresponding to the provided UUIDs exist.
     * If any of the specified users are not found, a {@link UserNotFoundException} is thrown.</p>
     *
     * @param userUuid a collection of unique user identifiers (UUIDs) to validate.
     * @throws UserNotFoundException if one or more users identified by the provided UUIDs do not exist.
     */
    void validateExistance(Collection<UUID> userUuid) throws UserNotFoundException;

    /**
     * Retrieves a list of all administrators.
     *
     * @return a list of {@link UserModel} representing the administrators in the system.
     */
    List<UserModel> findAllAdmins();
}
