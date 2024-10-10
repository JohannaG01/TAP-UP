package com.johannag.tapup.users.presentation.mappers;

import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithTokenModel;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserWithTokenResponseDTO;

public interface UserPresentationMapper {

    /**
     * Converts a {@link UserModel} object to a {@link UserResponseDTO} object.
     *
     * @param userModel the UserModel object to be converted
     * @return a UserResponseDTO representation of the provided UserModel
     */
    UserResponseDTO toUserResponseDTO(UserModel userModel);

    /**
     * Converts a {@link UserWithTokenModel} object into a {@link UserWithTokenResponseDTO} object.
     *
     * @param userWithTokenModel The {@link UserWithTokenModel} object containing user data and token.
     * @return A {@link UserWithTokenResponseDTO} object representing the user data and token formatted for the
     * response.
     */
    UserWithTokenResponseDTO toUserWithTokenResponseDTO(UserWithTokenModel userWithTokenModel);
}
