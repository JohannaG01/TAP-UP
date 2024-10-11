package com.johannag.tapup.users.application.mappers;

import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.presentation.dtos.requests.AddUserFundsRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.LogInUserRequestDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class UserApplicationMapperImpl implements UserApplicationMapper {

    private final TypeMap<CreateUserRequestDTO, CreateUserDTO.Builder> createUserDTOMapper;
    private final TypeMap<CreateUserDTO, CreateUserEntityDTO.Builder> createUserEntityDTOMapper;
    private final TypeMap<LogInUserRequestDTO, LogInUserDTO.Builder> logInUserRequestDTOMapper;
    private final TypeMap<AddUserFundsRequestDTO, AddUserFundsDTO.Builder> addUserFundsDTOMapper;
    private final TypeMap<AddUserFundsDTO, AddUserFundsToEntityDTO.Builder> addUserFundsToEntityDTOMapper;

    public UserApplicationMapperImpl() {
        createUserDTOMapper = builderTypeMapper(CreateUserRequestDTO.class, CreateUserDTO.Builder.class);
        createUserEntityDTOMapper = builderTypeMapper(CreateUserDTO.class, CreateUserEntityDTO.Builder.class);
        logInUserRequestDTOMapper = builderTypeMapper(LogInUserRequestDTO.class, LogInUserDTO.Builder.class);
        addUserFundsDTOMapper = builderTypeMapper(AddUserFundsRequestDTO.class, AddUserFundsDTO.Builder.class);
        addUserFundsToEntityDTOMapper = builderTypeMapper(AddUserFundsDTO.class, AddUserFundsToEntityDTO.Builder.class);
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

    @Override
    public LogInUserDTO toLogInUserDTO(LogInUserRequestDTO dto) {
        return logInUserRequestDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public AddUserFundsDTO toAddUserFundsDTO(UUID userUuid, AddUserFundsRequestDTO dto) {
        return addUserFundsDTOMapper
                .map(dto)
                .userUuid(userUuid)
                .build();
    }

    @Override
    public AddUserFundsToEntityDTO toAddUserFundsToEntityDTO(AddUserFundsDTO dto) {
        return addUserFundsToEntityDTOMapper
                .map(dto)
                .build();
    }
}
