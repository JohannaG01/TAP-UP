package com.johannag.tapup.authentication.presentation.mappers;

import com.johannag.tapup.authentication.domain.models.AuthTokenModel;
import com.johannag.tapup.authentication.presentation.dtos.responses.TokenResponseDTO;

public interface TokenPresentationMapper {

    /**
     * Converts a {@link AuthTokenModel} instance to a {@link TokenResponseDTO}.
     *
     * @param token the {@link AuthTokenModel} to be converted
     * @return the converted {@link TokenResponseDTO} instance
     */
    TokenResponseDTO toTokenResponseDTO(AuthTokenModel token);
}
