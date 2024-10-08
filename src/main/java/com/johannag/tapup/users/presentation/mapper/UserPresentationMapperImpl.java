package com.johannag.tapup.users.presentation.mapper;

import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.presentation.dtos.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserPresentationMapperImpl implements UserPresentationMapper {

    @Override
    public UserResponseDTO toUserResponseDTO(UserModel userModel) {
        return UserResponseDTO.builder()
                .uuid(userModel.getUuid())
                .email(userModel.getEmail())
                .name(userModel.getName())
                .lastName(userModel.getLastName())
                .balance(userModel.getBalance())
                .build();
    }
}
