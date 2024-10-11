package com.johannag.tapup.auth.presentation.mappers;

import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.auth.presentation.dtos.responses.AuthTokenResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class AuthTokenPresentationMapperImpl implements AuthTokenPresentationMapper {

    private final TypeMap<AuthTokenModel, AuthTokenResponseDTO.Builder> authTokenResponseDTOMapper;

    public AuthTokenPresentationMapperImpl() {
        authTokenResponseDTOMapper = builderTypeMapper(AuthTokenModel.class, AuthTokenResponseDTO.Builder.class);
    }

    @Override
    public AuthTokenResponseDTO toAuthTokenResponseDTO(AuthTokenModel token) {
        return authTokenResponseDTOMapper
                .map(token)
                .build();
    }
}
