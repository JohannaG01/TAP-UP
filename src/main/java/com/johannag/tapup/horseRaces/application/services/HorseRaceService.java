package com.johannag.tapup.horseRaces.application.services;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface HorseRaceService {

    /**
     * Creates a new horse race based on the provided DTO.
     *
     * @param dto the data transfer object containing the details of the horse race to be created.
     * @return a {@link HorseRaceModel} representing the created horse race.
     * @throws HorseNotAvailableException if one or more horses included in the race are not available.
     * @throws HorseNotFoundException     if one or more horses do not exist in the system.
     */
    HorseRaceModel create(CreateHorseRaceDTO dto) throws HorseNotAvailableException, HorseNotFoundException;

    /**
     * Updates a horse race with the provided details.
     *
     * @param dto the DTO containing the updated information for the horse race
     * @return the updated {@link HorseRaceModel} instance
     * @throws HorseRaceNotFoundException     if no horse race with the specified details is found
     * @throws InvalidHorseRaceStateException if the horse race is in a state that does not allow updates
     * @throws HorseNotAvailableException     if any horse from race is not available for new date
     */
    HorseRaceModel update(UpdateHorseRaceDTO dto) throws HorseRaceNotFoundException, InvalidHorseRaceStateException,
            HorseNotAvailableException;

    /**
     * Finds a {@link HorseRaceModel} by its UUID.
     *
     * @param uuid the UUID of the horse race to be found
     * @return the {@link HorseRaceModel} associated with the given UUID
     * @throws HorseRaceNotFoundException if no horse race is found with the provided UUID
     */
    HorseRaceModel findOneByUuid(UUID uuid) throws HorseRaceNotFoundException;

    /**
     * Finds and returns a paginated list of {@link HorseRaceModel} entities based on the provided
     * search criteria in the {@link FindHorseRacesDTO}.
     * This method applies filters such as horse UUID, race state, and time ranges to retrieve
     * horse races matching the specified conditions, with support for pagination.
     *
     * @param dto the {@link FindHorseRacesDTO} containing filter criteria for the search,
     *            such as race state, start and end times, horse UUID, and pagination details (page, size).
     *            It must not be null.
     * @return a {@link Page} of {@link HorseRaceModel} that matches the search criteria.
     * The page will contain the filtered horse races and pagination information.
     * @throws IllegalArgumentException if the provided {@code dto} is null.
     */
    Page<HorseRaceModel> findAll(FindHorseRacesDTO dto);

    /**
     * Finds a horse race model by the specified participant UUID.
     *
     * <p>This method retrieves the {@link HorseRaceModel} associated with the given participant UUID.
     * If no horse race model is found for the specified UUID, a {@link ParticipantNotFoundException}
     * is thrown.</p>
     *
     * @param participantUuid the UUID of the participant whose horse race model is to be retrieved.
     * @return the {@link HorseRaceModel} associated with the specified participant UUID.
     * @throws ParticipantNotFoundException if no horse race model is found for the specified participant UUID.
     */
    HorseRaceModel findByParticipantUuid(UUID participantUuid) throws ParticipantNotFoundException;
}
