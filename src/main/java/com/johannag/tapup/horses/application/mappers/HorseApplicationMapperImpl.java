package com.johannag.tapup.horses.application.mappers;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.UpdateHorseRequestDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseApplicationMapperImpl implements HorseApplicationMapper {

    private final TypeMap<CreateHorseRequestDTO, CreateHorseDTO.Builder> createHorseDTOMapper;
    private final TypeMap<CreateHorseDTO, CreateHorseEntityDTO.Builder> createHorseEntityDTOMapper;
    private final TypeMap<UpdateHorseRequestDTO, UpdateHorseDTO.Builder> updateHorseDTOMapper;
    private final TypeMap<UpdateHorseDTO, UpdateHorseEntityDTO.Builder> updateHorseEntityDTOMapper;

    public HorseApplicationMapperImpl() {
        createHorseDTOMapper = builderTypeMapper(CreateHorseRequestDTO.class, CreateHorseDTO.Builder.class);
        createHorseEntityDTOMapper = builderTypeMapper(CreateHorseDTO.class, CreateHorseEntityDTO.Builder.class);
        updateHorseDTOMapper = builderTypeMapper(UpdateHorseRequestDTO.class, UpdateHorseDTO.Builder.class);
        updateHorseEntityDTOMapper = builderTypeMapper(UpdateHorseDTO.class, UpdateHorseEntityDTO.Builder.class);
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

    @Override
    public UpdateHorseDTO toUpdateHorseDTO(UUID horseUuid, UpdateHorseRequestDTO dto) {
        return updateHorseDTOMapper
                .map(dto)
                .uuid(horseUuid)
                .build();
    }

    @Override
    public UpdateHorseEntityDTO toUpdateHorseEntityDTO(UpdateHorseDTO dto) {
        return updateHorseEntityDTOMapper
                .map(dto)
                .build();
    }
}
