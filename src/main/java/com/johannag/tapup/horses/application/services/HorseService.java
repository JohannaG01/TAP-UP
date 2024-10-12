package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.exceptions.InvalidHorseStateException;
import com.johannag.tapup.horses.domain.models.HorseModel;
import org.springframework.data.domain.Page;

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
     * @throws InvalidHorseStateException          if attempted to modify horse state to INACTIVE
     */
    HorseModel update(UpdateHorseDTO dto) throws HorseNotFoundException, CannotTransitionHorseStateException,
            InvalidHorseStateException;

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
     *         specified criteria. The page includes metadata about the total number of
     *         items, total pages, and whether there are more pages available.
     */
    Page<HorseModel> findAll(FindHorsesDTO dto);
}
