package com.johannag.tapup.users.application.mappers;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.presentation.dtos.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class UserApplicationMapperImpl implements UserApplicationMapper {

    @Override
    public CreateUserDTO toCreateUserDTO(CreateUserRequestDTO dto) {
        return CreateUserDTO.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .build();
    }

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

    @Override
    public UserModel toUserModel(CreateUserDTO dto) {
        return UserModel.builder()
                .uuid(UUID.randomUUID())
                .email(dto.getEmail())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .isAdmin(false)
                .balance(new BigDecimal(0))
                .build();
    }
}
