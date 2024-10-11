package com.johannag.tapup.auth.application.services;

import com.johannag.tapup.auth.application.exceptions.JwtTokenInvalidException;
import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.users.domain.models.UserModel;
import io.jsonwebtoken.Claims;


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
     * Validates a JWT (JSON Web Token) and extracts its claims.
     *
     * @param jwt The JWT string to validate.
     * @return A {@link Claims} object containing the claims extracted from the validated JWT.
     * @throws JwtTokenInvalidException if the JWT is invalid or cannot be parsed.
     */
    Claims validateJwtToken(String jwt) throws JwtTokenInvalidException;
}
