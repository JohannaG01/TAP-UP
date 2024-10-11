package com.johannag.tapup.horses.infrastructure.db.adapters;

import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;

public interface HorseRepository {

    /**
     * Checks if a {@link HorseEntity} exists with the specified code.
     *
     * @param code the unique code of the horse to check, must not be null or empty
     * @return {@code true} if a horse with the given code exists, {@code false} otherwise
     */
    boolean existsHorseByCode(String code);

    /**
     * Creates a new horse entity based on the provided {@link CreateHorseEntityDTO}.
     *
     * @param dto the DTO containing the details for the new horse entity
     * @return the created {@link HorseModel} instance representing the new horse
     */
    HorseModel upsert(CreateHorseEntityDTO dto);
}
