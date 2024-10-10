package com.johannag.tapup.tokens.application.services;

import com.johannag.tapup.tokens.domain.models.TokenModel;
import com.johannag.tapup.users.domain.models.UserModel;

public interface TokenService {

    /**
     * Generates a new {@link TokenModel} for the provided {@link UserModel}.
     * <p>
     * This method creates a token that can be used for authentication purposes.
     *
     * @param user The {@link UserModel} for which the token is being created.
     * @return A {@link TokenModel} containing the generated token and its associated metadata.
     */
    TokenModel createJWT(UserModel user);
}
