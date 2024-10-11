package com.johannag.tapup.horses.application.mappers;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseApplicationMapperImpl implements HorseApplicationMapper {

    private final TypeMap<CreateHorseRequestDTO, CreateHorseDTO.Builder> createHorseDTOMapper;
    private final TypeMap<CreateHorseDTO, CreateHorseEntityDTO.Builder> createHorseEntityDTOMapper;


    public HorseApplicationMapperImpl() {
        createHorseDTOMapper = builderTypeMapper(CreateHorseRequestDTO.class, CreateHorseDTO.Builder.class);
        createHorseEntityDTOMapper = builderTypeMapper(CreateHorseDTO.class, CreateHorseEntityDTO.Builder.class);
    }

    @Override
    public CreateHorseDTO toCreateHorseDTO(CreateHorseRequestDTO dto) {
        return createHorseDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public CreateHorseEntityDTO toCreateHorseEntityDTO(CreateHorseDTO dto) {
        return createHorseEntityDTOMapper
                .map(dto)
                .uuid(UUID.randomUUID())
                .state(HorseModelState.ACTIVE)
                .build();
    }
}
