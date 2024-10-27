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
    private final TypeMap<UserModel, UserResponseDTO.Builder> responseDTOMapper;

    @Autowired
    public UserPresentationMapperImpl(AuthTokenPresentationMapper authTokenPresentationMapper) {
        this.authTokenPresentationMapper = authTokenPresentationMapper;
        responseDTOMapper = builderTypeMapper(UserModel.class, UserResponseDTO.Builder.class);
    }

    @Override
    public UserResponseDTO toResponseDTO(UserModel userModel) {
        return responseDTOMapper
                .map(userModel)
                .build();
    }

    @Override
    public UserWithAuthTokenResponseDTO toResponseDTO(UserWithAuthTokenModel userWithAuthTokenModel) {
        AuthTokenResponseDTO authTokenResponseDTO =
                authTokenPresentationMapper.toResponseDTO(userWithAuthTokenModel.getToken());
        UserResponseDTO userResponseDTO = toResponseDTO(userWithAuthTokenModel.getUser());

        return UserWithAuthTokenResponseDTO.builder()
                .token(authTokenResponseDTO)
                .user(userResponseDTO)
                .build();
    }
}
