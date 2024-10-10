package com.johannag.tapup.users.domain.mappers;

import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class UserDomainMapperImpl implements UserDomainMapper {

    private final TypeMap<CreateUserEntityDTO, UserEntity.Builder> createUserEntityDTOMapper;
    private final TypeMap<UserEntity, UserModel.Builder> modelMapper;

    public UserDomainMapperImpl() {
        createUserEntityDTOMapper = builderTypeMapper(CreateUserEntityDTO.class, UserEntity.Builder.class);
        modelMapper = builderTypeMapper(UserEntity.class, UserModel.Builder.class);
    }

    @Override
    public UserEntity toEntity(CreateUserEntityDTO userModel) {
        return createUserEntityDTOMapper
                .map(userModel)
                .build();
    }

    @Override
    public UserModel toModel(UserEntity userEntity) {
        return modelMapper
                .map(userEntity)
                .build();
    }
}
