package com.johannag.tapup.auth.application.services;

import com.johannag.tapup.auth.application.exceptions.JwtTokenInvalidException;
import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.users.domain.models.UserModel;
import io.jsonwebtoken.Claims;


public interface AuthenticationService {

    /**
     * Creates a JWT (JSON Web Token) for the specified user.
     *
     * <p>This method generates a new JWT based on the provided user information and user ID.
     * The generated token can be used for authentication and authorization purposes.</p>
     *
     * @param id the unique identifier of the user for whom the JWT is being created
     * @param user the {@link UserModel} containing user details to include in the token
     * @return an {@link AuthTokenModel} representing the generated JWT
     */
    AuthTokenModel createJwtToken(Long id, UserModel user);

    /**
     * Validates a JWT (JSON Web Token) and extracts its claims.
     *
     * @param jwt The JWT string to validate.
     * @return A {@link Claims} object containing the claims extracted from the validated JWT.
     * @throws JwtTokenInvalidException if the JWT is invalid or cannot be parsed.
     */
    Claims validateJwtToken(String jwt) throws JwtTokenInvalidException;
}
