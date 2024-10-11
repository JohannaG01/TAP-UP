package com.johannag.tapup.users.presentation.mappers;

import com.johannag.tapup.auth.presentation.dtos.responses.AuthTokenResponseDTO;
import com.johannag.tapup.auth.presentation.mappers.AuthTokenPresentationMapper;
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

    private final AuthTokenPresentationMapper authTokenPresentationMapper;
    private final TypeMap<UserModel, UserResponseDTO.Builder> userResponseDTOMapper;

    @Autowired
    public UserPresentationMapperImpl(AuthTokenPresentationMapper authTokenPresentationMapper) {
        this.authTokenPresentationMapper = authTokenPresentationMapper;
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
        AuthTokenResponseDTO authTokenResponseDTO =
                authTokenPresentationMapper.toAuthTokenResponseDTO(userWithAuthTokenModel.getToken());
        UserResponseDTO userResponseDTO = toUserResponseDTO(userWithAuthTokenModel.getUser());

        return UserWithAuthTokenResponseDTO.builder()
                .token(authTokenResponseDTO)
                .user(userResponseDTO)
                .build();
    }
}
