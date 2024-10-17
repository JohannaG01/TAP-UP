package com.johannag.tapup.users.domain.mappers;

import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class UserDomainMapperImpl implements UserDomainMapper {

    private final TypeMap<CreateUserEntityDTO, UserEntity.Builder> entityMapper;
    private final TypeMap<UserEntity, UserModel.Builder> modelMapper;

    public UserDomainMapperImpl() {
        entityMapper = builderTypeMapper(CreateUserEntityDTO.class, UserEntity.Builder.class);
        modelMapper = builderTypeMapper(UserEntity.class, UserModel.Builder.class);
        modelMapper.addMappings(mapper -> mapper.skip(UserModel.Builder::bets));
    }

    @Override
    public UserEntity toEntity(CreateUserEntityDTO userModel) {
        return entityMapper
                .map(userModel)
                .build();
    }

    @Override
    public UserModel toModelWithoutBets(UserEntity userEntity) {
        return modelMapper
                .map(userEntity)
                .build();
    }
}
