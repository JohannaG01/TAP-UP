package com.johannag.tapup.tokens.presentation.mappers;

import com.johannag.tapup.tokens.domain.models.TokenModel;
import com.johannag.tapup.tokens.presentation.dtos.responses.TokenResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class TokenPresentationMapperImpl implements TokenPresentationMapper {

    private final TypeMap<TokenModel, TokenResponseDTO.Builder> tokenResponseDTOMapper;

    public TokenPresentationMapperImpl() {
        tokenResponseDTOMapper = builderTypeMapper(TokenModel.class, TokenResponseDTO.Builder.class);
    }

    @Override
    public TokenResponseDTO toTokenResponseDTO(TokenModel token) {
        return tokenResponseDTOMapper
                .map(token)
                .build();
    }
}
