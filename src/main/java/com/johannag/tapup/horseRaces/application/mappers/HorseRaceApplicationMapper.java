package com.johannag.tapup.horseRaces.application.mappers;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.CreateHorseRaceRequestDTO;

public interface HorseRaceApplicationMapper {

    /**
     * Converts a {@link CreateHorseRaceRequestDTO} to a {@link CreateHorseRaceDTO}.
     *
     * @param dto the {@link CreateHorseRaceRequestDTO} to be converted
     * @return a {@link CreateHorseRaceDTO} that represents the converted data
     */
    CreateHorseRaceDTO toCreateDTO(CreateHorseRaceRequestDTO dto);

    /**
     * Converts a {@link CreateHorseRaceDTO} into a {@link CreateHorseRaceEntityDTO}.
     *
     * <p>This method takes a data transfer object (DTO) representing the details of a horse race and
     * transforms it into an entity DTO suitable for use in creating a new horse race entity in the system.
     *
     * @param dto the {@link CreateHorseRaceDTO} containing the input data for the horse race
     * @return a {@link CreateHorseRaceEntityDTO} populated with the data from the provided {@link CreateHorseRaceDTO}
     */
    CreateHorseRaceEntityDTO toCreateEntityDTO(CreateHorseRaceDTO dto);
}
