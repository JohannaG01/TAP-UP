package com.johannag.tapup.horseRaces.application.mappers;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateParticipantEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.CreateHorseRaceRequestDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseRaceApplicationMapperImpl implements HorseRaceApplicationMapper {

    private final TypeMap<CreateHorseRaceRequestDTO, CreateHorseRaceDTO.Builder> createDTOMapper;

    public HorseRaceApplicationMapperImpl() {
        createDTOMapper = builderTypeMapper(CreateHorseRaceRequestDTO.class, CreateHorseRaceDTO.Builder.class);
    }

    @Override
    public CreateHorseRaceDTO toCreateDTO(CreateHorseRaceRequestDTO dto) {
        return createDTOMapper
                .map(dto)
                .build();
    }

    @Override
    public CreateHorseRaceEntityDTO toCreateEntityDTO(CreateHorseRaceDTO dto) {
        List<CreateParticipantEntityDTO> createParticipantEntityDTOS = dto.getHorsesUuids().stream()
                .map(this::toCreateParticipantEntityDTO)
                .toList();

        return CreateHorseRaceEntityDTO.builder()
                .uuid(UUID.randomUUID())
                .participants(createParticipantEntityDTOS)
                .startTime(dto.getStartTime())
                .state(HorseRaceModelState.SCHEDULED)
                .build();
    }

    private CreateParticipantEntityDTO toCreateParticipantEntityDTO(UUID horseUuid) {
        return CreateParticipantEntityDTO.builder()
                .uuid(UUID.randomUUID())
                .horseUuid(horseUuid)
                .build();
    }
}
