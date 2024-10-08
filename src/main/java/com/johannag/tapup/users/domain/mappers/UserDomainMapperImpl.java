package com.johannag.tapup.users.domain.mappers;

import com.johannag.tapup.configurations.UserSystemConfig;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserDomainMapperImpl implements UserDomainMapper {

    @Override
    public UserEntity toEntity(UserModel userModel) {
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
}
