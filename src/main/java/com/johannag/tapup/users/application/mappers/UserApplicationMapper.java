package com.johannag.tapup.users.application.mappers;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.presentation.dtos.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.UserResponseDTO;

public interface UserApplicationMapper {

    /**
     * Converts a {@link CreateUserRequestDTO} object to a {@link CreateUserDTO} object.
     *
     * @param dto the CreateUserRequestDTO object to be converted
     * @return a CreateUserDTO representation of the provided CreateUserRequestDTO
     */
    CreateUserDTO toCreateUserDTO(CreateUserRequestDTO dto);

    /**
     * Converts a {@link CreateUserDTO} to a {@link UserModel}.
     *
     * @param dto the {@link CreateUserDTO} to be converted
     * @return the corresponding {@link UserModel} representation of the provided DTO
     */
    UserModel toUserModel(CreateUserDTO dto);

}
