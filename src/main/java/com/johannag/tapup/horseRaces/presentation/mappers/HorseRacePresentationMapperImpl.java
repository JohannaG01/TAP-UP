package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.ParticipantResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseRacePresentationMapperImpl implements HorseRacePresentationMapper {

    private final ParticipantPresentationMapper participantPresentationMapper;
    private final TypeMap<HorseRaceModel, HorseRaceResponseDTO.Builder> responseDTOMapper;

    public HorseRacePresentationMapperImpl(ParticipantPresentationMapper participantPresentationMapper) {
        this.participantPresentationMapper = participantPresentationMapper;
        responseDTOMapper = builderTypeMapper(HorseRaceModel.class, HorseRaceResponseDTO.Builder.class);
        responseDTOMapper.addMappings(mapper -> mapper.skip(HorseRaceResponseDTO.Builder::participants));
    }

    @Override
    public HorseRaceResponseDTO toResponseDTO(HorseRaceModel model) {
        List<ParticipantResponseDTO> participants = model.getParticipants().stream()
                .map(participantPresentationMapper::toResponseDTOWithoutRace)
                .toList();

        return responseDTOMapper
                .map(model)
                .participants(participants)
                .build();
    }

    @Override
    public HorseRaceResponseDTO toResponseDTOWithoutParticipants(HorseRaceModel model) {
        return responseDTOMapper
                .map(model)
                .build();
    }

    @Override
    public Page<HorseRaceResponseDTO> toResponseDTO(Page<HorseRaceModel> models) {
        return models.map(this::toResponseDTO);
    }
}
