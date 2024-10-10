package com.johannag.tapup.users.application.services;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithTokenModel;

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
     * Authenticates a user using the provided login credentials and returns a {@link UserWithTokenModel}.
     *
     * @param dto The {@link LogInUserDTO} object containing the user's login credentials.
     * @return A {@link UserWithTokenModel} object that contains the authenticated user information and a generated
     * token.
     * @throws InvalidLoginCredentialsException if the login credentials are invalid, such as incorrect username or
     * password.
     */
    UserWithTokenModel logIn(LogInUserDTO dto) throws InvalidLoginCredentialsException;
}
