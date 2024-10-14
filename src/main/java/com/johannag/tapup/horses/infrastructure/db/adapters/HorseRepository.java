package com.johannag.tapup.horses.infrastructure.db.adapters;

import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
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
    Optional<HorseModel> findOneMaybeByUuid(UUID uuid);

    /**
     * Checks if a horse with the specified UUID is currently scheduled
     * for a match.
     *
     * @param uuid the unique identifier of the horse to check
     * @return {@code true} if the horse is scheduled for a match;
     * {@code false} otherwise
     */
    boolean isHorseInScheduledMatch(UUID uuid);

    /**
     * Updates the horse information based on the provided {@link UpdateHorseEntityDTO}.
     *
     * @param dto the data transfer object containing the updated horse information
     * @return an instance of {@link HorseModel} representing the updated horse
     */
    HorseModel update(UpdateHorseEntityDTO dto);

    /**
     * Deactivates the horse entity associated with the given UUID.
     *
     * <p>This method sets the state of the horse entity to inactive.
     *
     * @param uuid the UUID of the horse entity to be deactivated
     *             due to its current state
     */
    HorseModel deactivateByUuid(UUID uuid);

    /**
     * Retrieves a paginated list of horses based on the given search criteria.
     *
     * <p>This method accepts a {@link FindHorsesDTO} object, which contains the filters and
     * search parameters needed to query the list of horses. The results are returned
     * as a paginated {@link Page} of {@link HorseModel} objects.
     *
     * @param dto the {@link FindHorsesDTO} object that contains the search criteria
     * @return a {@link Page} of {@link HorseModel} objects that match the given criteria
     */
    Page<HorseModel> findAll(FindHorsesDTO dto);

    /**
     * Retrieves a list of active horse models based on a list of horse UUIDs.
     * <p>
     * This method allows for the retrieval of multiple horse entities by their unique identifiers.
     *
     * @param uuid a list of UUIDs representing the unique identifiers of the horses to retrieve
     * @return a list of {@link HorseModel} instances corresponding to the provided UUIDs.
     * If no horses are found, an empty list is returned.
     */
    List<HorseModel> findActiveByUuidIn(List<UUID> uuid);

    /**
     * Finds a list of horse models based on their UUIDs that are either scheduled for a race
     * before a specified date and time or have participated in a finished race after a specified date and time.
     *
     * @param uuids          A list of UUIDs representing the horses to be searched.
     * @param pastDateTime   The date and time before which the horses are scheduled for a race.
     * @param futureDateTime The date and time after which the horses have participated in a finished race.
     * @param raceDateTime   The specific date and time of the race to consider for scheduling.
     * @return A list of {@link HorseModel} objects that match the specified criteria.
     */
    List<HorseModel> findByUuidsInScheduledRaceBeforeOrInFinishedRaceAfter(List<UUID> uuids,
                                                                           LocalDateTime pastDateTime,
                                                                           LocalDateTime futureDateTime,
                                                                           LocalDateTime raceDateTime);
}
