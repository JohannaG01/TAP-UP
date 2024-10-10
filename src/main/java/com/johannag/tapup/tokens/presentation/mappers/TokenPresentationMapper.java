package com.johannag.tapup.tokens.presentation.mappers;

import com.johannag.tapup.tokens.domain.models.TokenModel;
import com.johannag.tapup.tokens.presentation.dtos.responses.TokenResponseDTO;

public interface TokenPresentationMapper {

    /**
     * Converts a {@link TokenModel} instance to a {@link TokenResponseDTO}.
     *
     * @param token the {@link TokenModel} to be converted
     * @return the converted {@link TokenResponseDTO} instance
     */
    TokenResponseDTO toTokenResponseDTO(TokenModel token);
}
