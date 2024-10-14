package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import com.johannag.tapup.horses.presentation.mappers.HorsePresentationMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class HorseRacePresentationMapperImpl implements HorseRacePresentationMapper {

    private final TypeMap<HorseRaceModel, HorseRaceResponseDTO.Builder> responseDTOMapper;

    public HorseRacePresentationMapperImpl() {
        responseDTOMapper = builderTypeMapper(HorseRaceModel.class, HorseRaceResponseDTO.Builder.class);
    }

    @Override
    public HorseRaceResponseDTO toResponseDTO(HorseRaceModel model) {
        return responseDTOMapper
                .map(model)
                .build();
    }
}
