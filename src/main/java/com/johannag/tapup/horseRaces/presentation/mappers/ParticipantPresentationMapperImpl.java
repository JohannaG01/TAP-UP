package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.ParticipantResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class ParticipantPresentationMapperImpl implements ParticipantPresentationMapper {

    private final TypeMap<ParticipantModel, ParticipantResponseDTO.Builder> responseDTOMapper;

    public ParticipantPresentationMapperImpl() {
        responseDTOMapper = builderTypeMapper(ParticipantModel.class, ParticipantResponseDTO.Builder.class);
    }

    @Override
    public ParticipantResponseDTO toResponseDTO(ParticipantModel model) {
        return responseDTOMapper
                .map(model)
                .build();
    }
}
