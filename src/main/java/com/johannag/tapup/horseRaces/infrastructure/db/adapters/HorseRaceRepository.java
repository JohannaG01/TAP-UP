package com.johannag.tapup.horseRaces.infrastructure.db.adapters;

import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;

public interface HorseRaceRepository {

    /**
     * Creates a new horse race model from the given {@link CreateHorseRaceEntityDTO}.
     *
     * <p>This method takes a data transfer object (DTO) that contains the details of a horse race entity
     * and creates a corresponding {@link HorseRaceModel} in the system. The created model represents
     * the newly added horse race and contains all relevant information from the provided entity DTO.
     *
     * @param dto the {@link CreateHorseRaceEntityDTO} containing the input data for the horse race entity
     * @return a {@link HorseRaceModel} representing the newly created horse race
     */
    HorseRaceModel create(CreateHorseRaceEntityDTO dto);
}
