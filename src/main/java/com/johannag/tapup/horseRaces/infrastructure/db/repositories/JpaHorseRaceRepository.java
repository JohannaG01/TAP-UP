package com.johannag.tapup.horseRaces.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaHorseRaceRepository extends JpaRepository<HorseRaceEntity, Long> {

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
}