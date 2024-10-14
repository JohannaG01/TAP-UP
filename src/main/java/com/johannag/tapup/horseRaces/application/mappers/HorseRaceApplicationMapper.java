package com.johannag.tapup.horseRaces.application.mappers;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.CreateHorseRaceRequestDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.UpdateHorseRaceRequestDTO;

import java.util.UUID;

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

    /**
     * Converts the specified {@link UpdateHorseRaceRequestDTO} to an {@link UpdateHorseRaceDTO}
     * for updating a horse race, using the provided horse race UUID.
     *
     * @param horseRaceUuid the UUID of the horse race to update
     * @param dto           the data transfer object containing the update information for the horse race
     * @return an {@link UpdateHorseRaceDTO} object populated with the provided data and horse race UUID
     */
    UpdateHorseRaceDTO toUpdateDTO(UUID horseRaceUuid, UpdateHorseRaceRequestDTO dto);

    /**
     * Converts the provided {@link UpdateHorseRaceDTO} to an {@link UpdateHorseRaceEntityDTO}.
     * <p>
     * This method maps the properties of the given DTO to the corresponding fields in the
     * {@link UpdateHorseRaceEntityDTO}.
     *
     * @param dto the DTO containing the update information for the horse race
     * @return an instance of {@link UpdateHorseRaceEntityDTO} populated with values from the provided DTO
     */
    UpdateHorseRaceEntityDTO toUpdateEntityDTO(UpdateHorseRaceDTO dto);
}
