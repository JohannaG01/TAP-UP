package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTemporallyInactivateHorseException;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;

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
     * @throws HorseNotFoundException                   if no horse entity is found for the provided identifier
     * @throws CannotTemporallyInactivateHorseException if horse is in schedule match and operation will temporally
     * inactivate it
     */
    HorseModel update(UpdateHorseDTO dto) throws HorseNotFoundException, CannotTemporallyInactivateHorseException;
}
