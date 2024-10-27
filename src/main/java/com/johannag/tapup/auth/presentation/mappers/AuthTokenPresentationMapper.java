package com.johannag.tapup.auth.presentation.mappers;

import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.auth.presentation.dtos.responses.AuthTokenResponseDTO;

public interface AuthTokenPresentationMapper {

    /**
     * Converts a {@link AuthTokenModel} instance to a {@link AuthTokenResponseDTO}.
     *
     * @param token the {@link AuthTokenModel} to be converted
     * @return the converted {@link AuthTokenResponseDTO} instance
     */
    AuthTokenResponseDTO toResponseDTO(AuthTokenModel token);
}
