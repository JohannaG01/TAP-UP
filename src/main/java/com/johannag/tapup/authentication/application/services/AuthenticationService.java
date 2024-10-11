package com.johannag.tapup.authentication.application.services;

import com.johannag.tapup.authentication.application.exceptions.JwtTokenInvalidException;
import com.johannag.tapup.authentication.domain.models.AuthTokenModel;
import com.johannag.tapup.users.domain.models.UserModel;


public interface AuthenticationService {

    /**
     * Generates a new {@link AuthTokenModel} for the provided {@link UserModel}.
     * <p>
     * This method creates a token that can be used for authentication purposes.
     *
     * @param user The {@link UserModel} for which the token is being created.
     * @return A {@link AuthTokenModel} containing the generated token and its associated metadata.
     */
    AuthTokenModel createJwtToken(UserModel user);

    /**
     * Validates the provided JSON Web Token (JWT).
     * <p>
     * This method checks the integrity and validity of the given JWT. It verifies
     * whether the token is well-formed, has not expired, and is signed correctly.
     * If the token is invalid or expired, an appropriate exception will be thrown.
     *
     * @param jwt the JSON Web Token to validate
     * @throws JwtTokenInvalidException if the token is invalid or expired
     */
    void validateJwtToken(String jwt) throws JwtTokenInvalidException;
}
