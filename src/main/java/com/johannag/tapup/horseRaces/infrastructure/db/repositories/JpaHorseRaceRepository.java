package com.johannag.tapup.horseRaces.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface JpaHorseRaceRepository extends JpaRepository<HorseRaceEntity, Long>, JpaSpecificationExecutor<HorseRaceEntity> {

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
     * Retrieves a paginated list of {@link HorseRaceEntity} based on the specified criteria.
     *
     * <p>This method utilizes a {@link Specification} to filter the results and applies an
     * {@link EntityGraph} to fetch associated participants and their horses eagerly. This helps to
     * optimize performance by minimizing the number of database queries required to retrieve related data.</p>
     *
     * @param spec    the {@link Specification} used to filter the horse race entities.
     *                If null, all entities will be returned.
     * @param pageable the pagination information, must not be null. This object contains the details
     *                 for the requested page, such as page number and size.
     * @return a {@link Page} containing the matching {@link HorseRaceEntity} instances.
     *         This page will be empty if no entities match the specification.
     */
    @NonNull
    @EntityGraph(attributePaths = {"participants", "participants.horse"})
    Page<HorseRaceEntity> findAll(Specification<HorseRaceEntity> spec, @NonNull Pageable pageable);

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