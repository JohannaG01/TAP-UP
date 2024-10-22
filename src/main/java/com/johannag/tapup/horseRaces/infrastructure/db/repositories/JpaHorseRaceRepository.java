package com.johannag.tapup.horseRaces.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;
import java.util.UUID;

public interface JpaHorseRaceRepository extends JpaRepository<HorseRaceEntity, Long>,
        JpaSpecificationExecutor<HorseRaceEntity> {

    /**
     * Retrieves a {@link HorseRaceEntity} by its UUID, including its participants
     * and their associated horses in a single fetch using an entity graph.
     *
     * @param uuid the unique identifier of the horse race
     * @return an {@link Optional} containing the {@link HorseRaceEntity} if found,
     * or an empty {@link Optional} if no horse race with the given UUID exists
     */
    @EntityGraph(attributePaths = {"participants", "participants.horse"})
    Optional<HorseRaceEntity> findOneMaybeByUuid(UUID uuid);

    /**
     * Retrieves a {@link HorseRaceEntity} by its UUID, using an {@link EntityGraph} to eagerly fetch
     * the related {@link ParticipantEntity} and their associated {@link HorseEntity}.
     * <p>
     * The {@link EntityGraph} is used to optimize the fetching of the relationships "participants"
     * and "participants.horse" in a single query, reducing the number of separate SELECT statements.
     * <p>
     * This method does not apply any locking strategy, so it is useful for read-only operations
     * where consistent, non-blocking data access is required.
     *
     * @param uuid the unique identifier of the horse race to retrieve
     * @return the {@link HorseRaceEntity} associated with the given UUID, including the participants and their horses
     */
    @EntityGraph(attributePaths = {"participants", "participants.horse"})
    HorseRaceEntity findOneByUuid(UUID uuid);


    /**
     * Retrieves a {@link HorseRaceEntity} by its UUID for update, acquiring a
     * pessimistic write lock to prevent concurrent modifications.
     *
     * @param uuid the unique identifier of the horse race
     * @return the {@link HorseRaceEntity} associated with the given UUID
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM HorseRaceEntity h WHERE h.uuid = :uuid")
    HorseRaceEntity findOneByUuidForUpdate(UUID uuid);

    /**
     * Retrieves a {@link HorseRaceEntity} by its unique identifier, acquiring a pessimistic write lock
     * to prevent concurrent updates.
     *
     * <p>This method performs an inner join with the participants of the horse race, ensuring that
     * only horse races with participants are returned.</p>
     *
     * @param uuid the unique identifier of the horse race to retrieve
     * @return the {@link HorseRaceEntity} corresponding to the provided UUID
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM HorseRaceEntity h INNER JOIN FETCH h.participants WHERE h.uuid = :uuid")
    HorseRaceEntity findOneFetchedByUuidForUpdate(UUID uuid);

    /**
     * Finds a horse race entity by the given participant UUID.
     *
     * <p>This method retrieves an optional {@link HorseRaceEntity} associated with the specified
     * participant UUID. If a horse race entity with the specified participant UUID is found,
     * the method returns an {@link Optional} containing the horse race entity; otherwise,
     * it returns an empty {@link Optional}.The results and applies an
     * {@link EntityGraph} to fetch associated participants and their horses eagerly.</p>
     *
     * @param uuid the UUID of the participant whose horse race entity is to be found.
     * @return an {@link Optional} containing the {@link HorseRaceEntity} if found, or an empty {@link Optional} if not.
     */
    @EntityGraph(attributePaths = {"participants", "participants.horse"})
    Optional<HorseRaceEntity> findOneMaybeByParticipants_Uuid(UUID uuid);
}