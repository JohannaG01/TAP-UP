package com.johannag.tapup.users.application.services;

import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;

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
     * Adds funds to a user account.
     *
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
}
