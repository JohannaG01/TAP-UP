package com.johannag.tapup.users.application.mappers;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.presentation.dtos.requests.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.LogInUserRequestDTO;

public interface UserApplicationMapper {

    /**
     * Converts a {@link CreateUserRequestDTO} object to a {@link CreateUserDTO} object.
     *
     * @param dto the CreateUserRequestDTO object to be converted
     * @return a CreateUserDTO representation of the provided CreateUserRequestDTO
     */
    CreateUserDTO toCreateUserDTO(CreateUserRequestDTO dto);

    /**
     * Converts a {@link CreateUserDTO} to a {@link CreateUserEntityDTO}.
     *
     * @param dto the {@link CreateUserDTO} to be converted
     * @return the corresponding {@link CreateUserEntityDTO} representation of the provided DTO
     */
    CreateUserEntityDTO toCreateUserEntityDTO(CreateUserDTO dto, String hashedPassword);

    /**
     * Converts a {@link LogInUserRequestDTO} object into a {@link LogInUserDTO} object.
     *
     * @param dto The {@link LogInUserRequestDTO} object containing the login data.
     * @return A {@link LogInUserDTO} object representing the user data ready for authentication.
     */
    LogInUserDTO toLogInUserDTO(LogInUserRequestDTO dto);

}
