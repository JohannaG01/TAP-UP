package com.johannag.tapup.users.application.mappers;

import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.presentation.dtos.requests.AddUserFundsRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.LogInUserRequestDTO;

import java.util.UUID;

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

    /**
     * Converts an {@link AddUserFundsRequestDTO} to an {@link AddUserFundsDTO}
     * associated with the specified user UUID.
     *
     * This method transforms the data contained in the provided
     * {@link AddUserFundsRequestDTO} into an instance of {@link AddUserFundsDTO},
     * linking it to the user identified by the given UUID. This is typically used
     * to prepare data for further processing or to interact with the application's
     * core logic for a specific user.
     *
     * @param userUuid the UUID of the user to which the funds are being added
     * @param dto the data transfer object containing the user funds request information
     * @return an {@link AddUserFundsDTO} instance populated with the data from the input DTO
     */
    AddUserFundsDTO toAddUserFundsDTO(UUID userUuid, AddUserFundsRequestDTO dto);

    /**
     * Converts an {@link AddUserFundsDTO} to an {@link AddUserFundsToEntityDTO}.
     *
     * @param dto the {@link AddUserFundsDTO} object to be converted. Must not be null.
     * @return the converted {@link AddUserFundsToEntityDTO} object.
     * @throws IllegalArgumentException if the provided {@code dto} is null.
     */
    AddUserFundsToEntityDTO toAddUserFundsToEntityDTO(AddUserFundsDTO dto);

}
