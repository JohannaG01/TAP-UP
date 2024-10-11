package com.johannag.tapup.horses.infrastructure.db.adapters;

import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;

import java.util.Optional;
import java.util.UUID;

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

    /**
     * Retrieves a {@link HorseModel} by its unique identifier (UUID).
     *
     * @param uuid the unique identifier of the horse to retrieve
     * @return an {@link Optional} containing the {@link HorseModel} if found, or an empty {@link Optional} if no
     * horse exists with the given UUID
     */
    Optional<HorseModel> findMaybeByUuid(UUID uuid);

    /**
     * Checks if a horse with the specified UUID is currently scheduled
     * for a match.
     *
     * @param uuid the unique identifier of the horse to check
     * @return {@code true} if the horse is scheduled for a match;
     *         {@code false} otherwise
     */
    boolean isHorseInScheduledMatch(UUID uuid);

    /**
     * Updates the horse information based on the provided {@link UpdateHorseEntityDTO}.
     *
     * @param dto the data transfer object containing the updated horse information
     * @return an instance of {@link HorseModel} representing the updated horse
     */
    HorseModel update(UpdateHorseEntityDTO dto);
}
