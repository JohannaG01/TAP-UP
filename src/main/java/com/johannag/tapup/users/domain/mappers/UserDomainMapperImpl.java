package com.johannag.tapup.users.domain.mappers;

import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDomainMapperImpl implements UserDomainMapper {

    @Override
    public UserEntity toEntity(CreateUserEntityDTO userModel) {
        return UserEntity.builder()
                .uuid(userModel.getUuid())
                .email(userModel.getEmail())
                .name(userModel.getName())
                .lastName(userModel.getLastName())
                .isAdmin(userModel.getIsAdmin())
                .balance(userModel.getBalance())
                .hashedPassword(userModel.getHashedPassword())
                .build();
    }

    @Override
    public UserModel toModel(UserEntity userEntity) {
        return UserModel.builder()
                .uuid(userEntity.getUuid())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .isAdmin(userEntity.getIsAdmin())
                .balance(userEntity.getBalance())
                .hashedPassword(userEntity.getHashedPassword())
                .build();
    }
}
