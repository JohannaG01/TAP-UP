package com.johannag.tapup.horses.application.services;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
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
}
