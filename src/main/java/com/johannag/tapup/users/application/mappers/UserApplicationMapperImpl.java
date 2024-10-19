package com.johannag.tapup.users.application.mappers;

import com.johannag.tapup.users.application.dtos.*;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.dtos.SubtractUserFundsToEntityDTO;
import com.johannag.tapup.users.presentation.dtos.requests.AddUserFundsRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.LogInUserRequestDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class UserApplicationMapperImpl implements UserApplicationMapper {

    private final TypeMap<CreateUserRequestDTO, CreateUserDTO.Builder> createDTOMapper;
    private final TypeMap<CreateUserDTO, CreateUserEntityDTO.Builder> createEntityDTOMapper;
    private final TypeMap<LogInUserRequestDTO, LogInUserDTO.Builder> logInRequestDTOMapper;
    private final TypeMap<AddUserFundsRequestDTO, AddUserFundsDTO.Builder> addFundsDTOMapper;
    private final TypeMap<AddUserFundsDTO, AddUserFundsToEntityDTO.Builder> addFundsToEntityDTOMapper;
    private final TypeMap<SubtractUserFundsDTO, SubtractUserFundsToEntityDTO.Builder> subtractFundsToEntityDTOMapper;


    public UserApplicationMapperImpl() {
        createDTOMapper = builderTypeMapper(CreateUserRequestDTO.class, CreateUserDTO.Builder.class);
        createEntityDTOMapper = builderTypeMapper(CreateUserDTO.class, CreateUserEntityDTO.Builder.class);
        logInRequestDTOMapper = builderTypeMapper(LogInUserRequestDTO.class, LogInUserDTO.Builder.class);
        addFundsDTOMapper = builderTypeMapper(AddUserFundsRequestDTO.class, AddUserFundsDTO.Builder.class);
        addFundsToEntityDTOMapper = builderTypeMapper(AddUserFundsDTO.class, AddUserFundsToEntityDTO.Builder.class);
        subtractFundsToEntityDTOMapper =
                builderTypeMapper(SubtractUserFundsDTO.class, SubtractUserFundsToEntityDTO.Builder.class);
    }

    @Override
    public CreateUserDTO toCreateDTO(CreateUserRequestDTO dto) {
        return createDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public CreateUserEntityDTO toCreateEntityDTO(CreateUserDTO dto, String hashedPassword) {
        return createEntityDTOMapper
                .map(dto)
                .uuid(UUID.randomUUID())
                .isAdmin(false)
                .balance(new BigDecimal(0))
                .hashedPassword(hashedPassword)
                .build();
    }

    @Override
    public LogInUserDTO toLogInDTO(LogInUserRequestDTO dto) {
        return logInRequestDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public AddUserFundsDTO toAddFundsDTO(UUID userUuid, AddUserFundsRequestDTO dto) {
        return addFundsDTOMapper
                .map(dto)
                .userUuid(userUuid)
                .build();
    }

    @Override
    public AddUserFundsToEntityDTO toAddFundsToEntitiesDTO(AddUserFundsDTO dto) {
        return addFundsToEntityDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public List<AddUserFundsToEntityDTO> toAddFundsToEntitiesDTO(List<AddUserFundsDTO> dtos) {
        return dtos.stream()
                .map(this::toAddFundsToEntitiesDTO)
                .toList();
    }


    @Override
    public SubtractUserFundsToEntityDTO toSubtractFundsToEntityDTO(SubtractUserFundsDTO dto) {
        return subtractFundsToEntityDTOMapper
                .map(dto)
                .build();
    }
}
