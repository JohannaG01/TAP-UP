package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.ParticipantResponseDTO;
import com.johannag.tapup.horses.presentation.mappers.HorsePresentationMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class ParticipantPresentationMapperImpl implements ParticipantPresentationMapper {

    private final HorsePresentationMapper horsePresentationMapper;
    private final HorseRacePresentationMapper horseRacePresentationMapper;
    private final TypeMap<ParticipantModel, ParticipantResponseDTO.Builder> responseDTOMapper;

    public ParticipantPresentationMapperImpl(HorsePresentationMapper horsePresentationMapper,
                                             @Lazy HorseRacePresentationMapper horseRacePresentationMapper) {
        this.horsePresentationMapper = horsePresentationMapper;
        this.horseRacePresentationMapper = horseRacePresentationMapper;
        responseDTOMapper = builderTypeMapper(ParticipantModel.class, ParticipantResponseDTO.Builder.class);
        responseDTOMapper.addMappings(mapper -> mapper.skip(ParticipantResponseDTO.Builder::horse));
        responseDTOMapper.addMappings(mapper -> mapper.skip(ParticipantResponseDTO.Builder::horseRace));
    }

    @Override
    public ParticipantResponseDTO toResponseDTOWithoutRace(ParticipantModel model) {
        return responseDTOMapper
                .map(model)
                .horse(horsePresentationMapper.toResponseDTO(model.getHorse()))
                .build();
    }

    @Override
    public ParticipantResponseDTO toResponseDTO(ParticipantModel model) {
        return responseDTOMapper
                .map(model)
                .horseRace(horseRacePresentationMapper.toResponseDTOWithoutParticipants(model.getHorseRace()))
                .horse(horsePresentationMapper.toResponseDTO(model.getHorse()))
                .build();
    }
}
