package com.johannag.tapup.users.domain.mappers;

import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;


public interface UserDomainMapper {

    /**
     * Converts a {@link CreateUserEntityDTO} to a {@link UserEntity}.
     *
     * @param userEntityDTO the CreateUserEntityDTO to be converted
     * @return the converted UserEntity
     */
    UserEntity toEntity(CreateUserEntityDTO userEntityDTO);

    /**
     * Converts a {@link UserEntity} to a {@link UserModel}.
     * <p>
     * This method is responsible for transforming the given {@link UserEntity} object,
     * into a {@link UserModel} that can be used in the application's domain logic.
     * </p>
     *
     * @param userEntity the {@link UserEntity} instance to be converted
     * @return the corresponding {@link UserModel} representing the user; never null
     */
    UserModel toModel(UserEntity userEntity);
}
