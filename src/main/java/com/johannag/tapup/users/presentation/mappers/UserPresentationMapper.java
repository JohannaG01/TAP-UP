package com.johannag.tapup.users.presentation.mappers;

import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserWithAuthTokenResponseDTO;

public interface UserPresentationMapper {

    /**
     * Converts a {@link UserModel} object to a {@link UserResponseDTO} object.
     *
     * @param userModel the UserModel object to be converted
     * @return a UserResponseDTO representation of the provided UserModel
     */
    UserResponseDTO toResponseDTO(UserModel userModel);

    /**
     * Converts a {@link UserWithAuthTokenModel} object into a {@link UserWithAuthTokenResponseDTO} object.
     *
     * @param userWithAuthTokenModel The {@link UserWithAuthTokenModel} object containing user data and token.
     * @return A {@link UserWithAuthTokenResponseDTO} object representing the user data and token formatted for the
     * response.
     */
    UserWithAuthTokenResponseDTO toResponseDTO(UserWithAuthTokenModel userWithAuthTokenModel);
}
