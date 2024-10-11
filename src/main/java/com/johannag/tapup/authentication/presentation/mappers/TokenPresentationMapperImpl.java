package com.johannag.tapup.authentication.presentation.mappers;

import com.johannag.tapup.authentication.domain.models.AuthTokenModel;
import com.johannag.tapup.authentication.presentation.dtos.responses.TokenResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class TokenPresentationMapperImpl implements TokenPresentationMapper {

    private final TypeMap<AuthTokenModel, TokenResponseDTO.Builder> tokenResponseDTOMapper;

    public TokenPresentationMapperImpl() {
        tokenResponseDTOMapper = builderTypeMapper(AuthTokenModel.class, TokenResponseDTO.Builder.class);
    }

    @Override
    public TokenResponseDTO toTokenResponseDTO(AuthTokenModel token) {
        return tokenResponseDTOMapper
                .map(token)
                .build();
    }
}
