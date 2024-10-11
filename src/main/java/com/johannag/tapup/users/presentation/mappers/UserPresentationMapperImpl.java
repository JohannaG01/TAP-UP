package com.johannag.tapup.users.presentation.mappers;

import com.johannag.tapup.authentication.presentation.dtos.responses.TokenResponseDTO;
import com.johannag.tapup.authentication.presentation.mappers.TokenPresentationMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserWithAuthTokenResponseDTO;
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

    @Autowired
    public UserPresentationMapperImpl(TokenPresentationMapper tokenPresentationMapper) {
        this.tokenPresentationMapper = tokenPresentationMapper;
        userResponseDTOMapper = builderTypeMapper(UserModel.class, UserResponseDTO.Builder.class);
    }

    @Override
    public UserResponseDTO toUserResponseDTO(UserModel userModel) {
        return userResponseDTOMapper
                .map(userModel)
                .build();
    }

    @Override
    public UserWithAuthTokenResponseDTO toUserWithTokenResponseDTO(UserWithAuthTokenModel userWithAuthTokenModel) {
        TokenResponseDTO tokenResponseDTO =
                tokenPresentationMapper.toTokenResponseDTO(userWithAuthTokenModel.getToken());
        UserResponseDTO userResponseDTO = toUserResponseDTO(userWithAuthTokenModel.getUser());

        return UserWithAuthTokenResponseDTO.builder()
                .token(tokenResponseDTO)
                .user(userResponseDTO)
                .build();
    }
}
