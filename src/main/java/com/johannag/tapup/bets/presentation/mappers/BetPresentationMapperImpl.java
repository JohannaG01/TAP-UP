package com.johannag.tapup.bets.presentation.mappers;

import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;
import com.johannag.tapup.bets.presentation.dtos.responses.BetStatisticsDTO;
import com.johannag.tapup.bets.presentation.dtos.responses.BetSummaryDTO;
import com.johannag.tapup.horseRaces.presentation.mappers.ParticipantPresentationMapper;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import com.johannag.tapup.horses.presentation.mappers.HorsePresentationMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class BetPresentationMapperImpl implements BetPresentationMapper {

    private final ParticipantPresentationMapper participantPresentationMapper;
    private final HorsePresentationMapper horsePresentationMapper;
    private final TypeMap<BetModel, BetResponseDTO.Builder> responseDTOMapper;
    private final TypeMap<BetSummaryModel, BetSummaryDTO.Builder> responseSummaryDTOMapper;
    private final TypeMap<BetStatisticsModel, BetStatisticsDTO.Builder> responseStatisticsDTOMapper;


    public BetPresentationMapperImpl(ParticipantPresentationMapper participantPresentationMapper,
                                     HorsePresentationMapper horsePresentationMapper) {
        this.participantPresentationMapper = participantPresentationMapper;
        this.horsePresentationMapper = horsePresentationMapper;

        responseDTOMapper = builderTypeMapper(BetModel.class, BetResponseDTO.Builder.class);
        responseDTOMapper.addMappings(mapper -> mapper.skip(BetResponseDTO.Builder::participant));

        responseSummaryDTOMapper = builderTypeMapper(BetSummaryModel.class, BetSummaryDTO.Builder.class);
        responseSummaryDTOMapper.addMappings(mapper -> mapper.skip(BetSummaryDTO.Builder::horse));

        responseStatisticsDTOMapper = builderTypeMapper(BetStatisticsModel.class, BetStatisticsDTO.Builder.class);
        responseStatisticsDTOMapper.addMappings(mapper -> mapper.skip(BetStatisticsDTO.Builder::bets));
    }

    @Override
    public BetResponseDTO toResponseDTO(BetModel model) {
        return responseDTOMapper
                .map(model)
                .participant(participantPresentationMapper.toResponseDTO(model.getParticipant()))
                .build();
    }

    @Override
    public Page<BetResponseDTO> toResponseDTO(Page<BetModel> models) {
        return models.map(this::toResponseDTO);
    }

    @Override
    public List<BetSummaryDTO> toResponseDTO(List<BetSummaryModel> models) {
        return models.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public BetStatisticsDTO toResponseDTO(BetStatisticsModel model) {
        List<BetSummaryDTO> betSummariesDTO = model.getBets().stream()
                .map(this::toResponseDTO)
                .toList();

        return responseStatisticsDTOMapper
                .map(model)
                .bets(betSummariesDTO)
                .build();
    }

    private BetSummaryDTO toResponseDTO(BetSummaryModel model) {
        HorseResponseDTO horse = horsePresentationMapper.toResponseDTO(model.getHorse());

        return responseSummaryDTOMapper
                .map(model)
                .horse(horse)
                .build();
    }
}
