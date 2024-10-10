package com.johannag.tapup.users.presentation.mappers;

import com.johannag.tapup.tokens.presentation.dtos.responses.TokenResponseDTO;
import com.johannag.tapup.tokens.presentation.mappers.TokenPresentationMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithTokenModel;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserWithTokenResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
@AllArgsConstructor
public class UserPresentationMapperImpl implements UserPresentationMapper {

    private final TokenPresentationMapper tokenPresentationMapper;
    private final TypeMap<UserModel, UserResponseDTO.Builder> userResponseDTOMapper;
    private final TypeMap<UserWithTokenModel, UserWithTokenResponseDTO.Builder> userWithTokenResponseDTOMapper;

    @Autowired
    public UserPresentationMapperImpl(TokenPresentationMapper tokenPresentationMapper) {
        this.tokenPresentationMapper = tokenPresentationMapper;

        userResponseDTOMapper = builderTypeMapper(UserModel.class, UserResponseDTO.Builder.class);
        userWithTokenResponseDTOMapper = builderTypeMapper(UserWithTokenModel.class,
                UserWithTokenResponseDTO.Builder.class);
    }

    @Override
    public UserResponseDTO toUserResponseDTO(UserModel userModel) {
        return userResponseDTOMapper
                .map(userModel)
                .build();
    }

    @Override
    public UserWithTokenResponseDTO toUserWithTokenResponseDTO(UserWithTokenModel userWithTokenModel) {
        TokenResponseDTO tokenResponseDTO = tokenPresentationMapper.toTokenResponseDTO(userWithTokenModel.getToken());
        UserResponseDTO userResponseDTO = toUserResponseDTO(userWithTokenModel.getUser());

        return UserWithTokenResponseDTO.builder()
                .token(tokenResponseDTO)
                .user(userResponseDTO)
                .build();
    }
}
