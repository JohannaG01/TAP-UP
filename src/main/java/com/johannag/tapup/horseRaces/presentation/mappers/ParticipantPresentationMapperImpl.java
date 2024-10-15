package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.ParticipantResponseDTO;
import com.johannag.tapup.horses.presentation.mappers.HorsePresentationMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class ParticipantPresentationMapperImpl implements ParticipantPresentationMapper {

    private final HorsePresentationMapper horsePresentationMapper;
    private final TypeMap<ParticipantModel, ParticipantResponseDTO.Builder> responseDTOMapper;

    public ParticipantPresentationMapperImpl(HorsePresentationMapper horsePresentationMapper) {
        this.horsePresentationMapper = horsePresentationMapper;
        responseDTOMapper = builderTypeMapper(ParticipantModel.class, ParticipantResponseDTO.Builder.class);
        responseDTOMapper.addMappings(mapper -> mapper.skip(ParticipantResponseDTO.Builder::horse));
    }

    @Override
    public ParticipantResponseDTO toResponseDTO(ParticipantModel model) {
        return responseDTOMapper
                .map(model)
                .horse(horsePresentationMapper.toResponseDTO(model.getHorse()))
                .build();
    }
}
