package com.johannag.tapup.horseRaces.application.services;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;

public interface HorseRaceService {

    /**
     * Creates a new horse race based on the provided DTO.
     *
     * @param dto the data transfer object containing the details of the horse race to be created.
     * @return a {@link HorseRaceModel} representing the created horse race.
     * @throws HorseNotAvailableException if one or more horses included in the race are not available.
     * @throws HorseNotFoundException if one or more horses do not exist in the system.
     */
    HorseRaceModel create(CreateHorseRaceDTO dto) throws HorseNotAvailableException, HorseNotFoundException;
}
