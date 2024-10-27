package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.domain.models.HorseModel;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HorseService {

    /**
     * Creates a new horse based on the provided {@link CreateHorseDTO}.
     * <p>
     * This method takes a data transfer object (DTO) containing the necessary information
     * to create a horse and persists it to the system. If a horse with the same attributes
     * already exists, a {@link HorseAlreadyExistsException} is thrown.
     *
     * @param dto the {@link CreateHorseDTO} containing the details required to create a new horse.
     * @return the newly created {@link HorseModel} representing the horse entity.
     * @throws HorseAlreadyExistsException if a horse with the same characteristics already exists.
     */
    HorseModel create(CreateHorseDTO dto) throws HorseAlreadyExistsException;

    /**
     * Updates an existing horse entity with the provided {@link UpdateHorseDTO} data.
     *
     * @param dto the {@link UpdateHorseDTO} containing the data to update
     * @return the updated {@link HorseModel} object
     * @throws HorseNotFoundException              if no horse entity is found for the provided identifier
     * @throws CannotTransitionHorseStateException if horse is in schedule race and operation will temporally
     *                                             inactivate it
     */
    HorseModel update(UpdateHorseDTO dto) throws HorseNotFoundException, CannotTransitionHorseStateException;

    /**
     * Deletes a horse entity by its UUID.
     *
     * @param uuid the UUID of the horse to be deleted.
     * @return the {@link HorseModel} representing the deleted horse.
     * @throws HorseNotFoundException              if no horse with the given UUID is found.
     * @throws CannotTransitionHorseStateException if horse is in schedule race
     */
    HorseModel delete(UUID uuid) throws HorseNotFoundException, CannotTransitionHorseStateException;

    /**
     * Retrieves a paginated list of {@link HorseModel} based on the specified
     * criteria in the {@link FindHorsesDTO}. The results can be filtered by
     * horse states and paginated according to the requested page number and size.
     *
     * @param dto The data transfer object containing pagination and filtering criteria,
     *            including page number, size, and an optional list of horse states.
     * @return A {@link Page} containing a list of {@link HorseModel} that match the
     * specified criteria. The page includes metadata about the total number of
     * items, total pages, and whether there are more pages available.
     */
    Page<HorseModel> findAll(FindHorsesDTO dto);

    /**
     * Finds a horse by its unique identifier (UUID).
     *
     * @param uuid the unique identifier of the horse
     * @return the {@link HorseModel} associated with the given UUID
     * @throws HorseNotFoundException if no horse is found with the provided UUID
     */
    HorseModel findOneByUuid(UUID uuid) throws HorseNotFoundException;

    /**
     * Validates the availability of the horses identified by the given UUIDs for a specified race start time.
     * This method checks if the horses are available and not already participating in other races at the
     * provided time. Optionally, it can exclude certain horse races from the validation.
     *
     * @param uuids                   the list of UUIDs of the horses to be validated
     * @param raceStartTime           the start time of the race for which availability is being checked
     * @param horseRaceUuidsToExclude a list of horse race UUIDs to be excluded from the validation,
     *                                typically used when updating an existing race to avoid conflicts
     * @throws HorseNotFoundException     if any of the horses identified by the UUIDs cannot be found
     * @throws HorseNotAvailableException if any of the horses are not available due to participation in
     *                                    another race at the given time
     */
    void validateHorsesAvailability(List<UUID> uuids, LocalDateTime raceStartTime, List<UUID> horseRaceUuidsToExclude)
            throws HorseNotFoundException, HorseNotAvailableException;
}
