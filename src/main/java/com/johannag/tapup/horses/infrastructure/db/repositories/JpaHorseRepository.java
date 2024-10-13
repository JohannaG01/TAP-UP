package com.johannag.tapup.horses.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaHorseRepository extends JpaRepository<HorseEntity, Long>, JpaSpecificationExecutor<HorseEntity> {

    /**
     * Checks if a horse entity exists with the specified code and state.
     *
     * @param code   the unique code of the horse entity to check for
     * @param states a list of possible states for the horse entity
     * @return {@code true} if a horse entity with the specified code and one of the specified states exists,
     * {@code false} otherwise
     */
    boolean existsByCodeAndStateIn(String code, List<HorseEntityState> states);

    /**
     * Finds a HorseEntity by its code with a pessimistic lock.
     *
     * @param code the code of the horse
     * @return an Optional containing the found HorseEntity, or empty if not found
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM HorseEntity h WHERE h.code = :code")
    Optional<HorseEntity> findMaybeOneByCodeForUpdate(String code);

    /**
     * Retrieves an optional {@link HorseEntity} by its unique identifier (UUID) and a list of allowed states.
     *
     * @param uuid   the unique identifier of the horse to retrieve
     * @param states a list of {@link HorseEntityState} representing the valid states of the horse
     * @return an {@link Optional} containing the {@link HorseEntity} if found with the specified UUID and state,
     * or an empty {@link Optional} if no horse exists with the given UUID in the specified states
     */
    Optional<HorseEntity> findMaybeOneByUuidAndStateIn(UUID uuid, List<HorseEntityState> states);

    /**
     * Checks if a {@link HorseEntity} exists with the specified UUID and is participating
     * in a horse race that has the given state.
     *
     * @param uuid  the UUID of the horse entity to check
     * @param state the state of the horse race to match
     * @return {@code true} if a horse with the specified UUID exists and is participating
     * in a race with the given state, {@code false} otherwise
     */
    boolean existsByUuidAndParticipations_HorseRace_State(UUID uuid, HorseRaceEntityState state);

    /**
     * Retrieves a {@link HorseEntity} by its UUID, applying a pessimistic write lock
     * to prevent concurrent modifications.
     *
     * @param uuid the UUID of the horse entity to retrieve
     * @return the {@link HorseEntity} associated with the specified UUID, or {@code null}
     * if no such entity exists
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM HorseEntity h WHERE h.uuid = :uuid")
    HorseEntity findOneByUuidForUpdate(UUID uuid);
}