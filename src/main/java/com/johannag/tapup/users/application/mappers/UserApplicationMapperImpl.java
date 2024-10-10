package com.johannag.tapup.users.application.mappers;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.presentation.dtos.CreateUserRequestDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static com.johannag.tapup.globals.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class UserApplicationMapperImpl implements UserApplicationMapper {

    private final TypeMap<CreateUserRequestDTO, CreateUserDTO.Builder> createUserDTOMapper;
    private final TypeMap<CreateUserDTO, CreateUserEntityDTO.Builder> createUserEntityDTOMapper;


    public UserApplicationMapperImpl() {
        createUserDTOMapper = builderTypeMapper(CreateUserRequestDTO.class, CreateUserDTO.Builder.class);
        createUserEntityDTOMapper = builderTypeMapper(CreateUserDTO.class, CreateUserEntityDTO.Builder.class);
    }

    @Override
    public CreateUserDTO toCreateUserDTO(CreateUserRequestDTO dto) {
        return createUserDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public CreateUserEntityDTO toCreateUserEntityDTO(CreateUserDTO dto, String hashedPassword) {
        return createUserEntityDTOMapper
                .map(dto)
                .uuid(UUID.randomUUID())
                .isAdmin(false)
                .balance(new BigDecimal(0))
                .hashedPassword(hashedPassword)
                .build();
    }
}
