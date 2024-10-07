package com.johannag.tapup.users.domain.mappers;

import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;

public interface UserDomainMapper {

    /**
     * Converts a UserModel to a UserEntity.
     *
     * @param userModel the UserModel to be converted
     * @return the converted UserEntity
     */
    UserEntity toEntity(UserModel userModel);
}
