package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;

public interface HorseRacePresentationMapper {

    /**
     * Converts a {@link HorseRaceModel} entity to a {@link HorseRaceResponseDTO}.
     *
     * @param model the {@link HorseRaceModel} entity to be converted
     * @return a {@link HorseRaceResponseDTO} representing the converted entity
     */
    HorseRaceResponseDTO toResponseDTO(HorseRaceModel model);
}
