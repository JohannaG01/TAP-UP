package com.johannag.tapup.bets.presentation.mappers;

import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;
import com.johannag.tapup.horseRaces.presentation.mappers.ParticipantPresentationMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class BetPresentationMapperImpl implements BetPresentationMapper {

    private final ParticipantPresentationMapper participantPresentationMapper;
    private final TypeMap<BetModel, BetResponseDTO.Builder> responseDTOMapper;

    public BetPresentationMapperImpl(ParticipantPresentationMapper participantPresentationMapper) {
        this.participantPresentationMapper = participantPresentationMapper;
        responseDTOMapper = builderTypeMapper(BetModel.class, BetResponseDTO.Builder.class);
        responseDTOMapper.addMappings(mapper -> mapper.skip(BetResponseDTO.Builder::participant));
    }

    @Override
    public BetResponseDTO toResponseDTO(BetModel model) {
        return responseDTOMapper
                .map(model)
                .participant(participantPresentationMapper.toResponseDTO(model.getParticipant()))
                .build();
    }
}
