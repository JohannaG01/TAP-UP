package com.johannag.tapup.users.presentation.mapper;

import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.presentation.dtos.UserResponseDTO;

public interface UserPresentationMapper {

    /**
     * Converts a {@link UserModel} object to a {@link UserResponseDTO} object.
     *
     * @param userModel the UserModel object to be converted
     * @return a UserResponseDTO representation of the provided UserModel
     */
    UserResponseDTO toUserResponseDTO(UserModel userModel);
}
