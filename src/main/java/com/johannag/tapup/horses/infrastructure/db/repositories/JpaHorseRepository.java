package com.johannag.tapup.horses.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.dtos.FindByUuidsStateAndDatesQuery;
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
    Optional<HorseEntity> findOneMaybeByUuidAndStateIn(UUID uuid, List<HorseEntityState> states);

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

    /**
     * Retrieves a {@code list} of a {@link HorseEntity} based on a list of horse UUIDs.
     * <p>
     * This method allows for the retrieval of multiple horse entities by their unique identifiers.
     *
     * @param uuid a list of UUIDs representing the unique identifiers of the horses to retrieve
     * @return a list of {@link HorseModel} instances corresponding to the provided UUIDs.
     * If no horses are found, an empty list is returned.
     */
    List<HorseEntity> findByUuidIn(List<UUID> uuid);

    /**
     * Finds and retrieves a list of horse entities based on their UUIDs and their state.
     *
     * @param uuid  a list of UUIDs representing the horses to search for.
     * @param state the state of the horses to filter by.
     * @return a list of {@link HorseEntity} that match the specified UUIDs and state.
     * If no matching horses are found, an empty list is returned.
     */
    List<HorseEntity> findByUuidInAndState(List<UUID> uuid, HorseEntityState state);

    /**
     * Finds a list of horse entities based on their UUIDs, checking if they are
     * scheduled for races in certain states and within specified date ranges.
     *
     * <p>This method will retrieve horses that are either participating in past races
     * starting from a specified date or are scheduled for future races before a given
     * end date.</p>
     *
     * @param dto the data transfer object containing the criteria for searching horses,
     *            including:
     *            <ul>
     *              <li>uuids: List of UUIDs of the horses to be queried.</li>
     *              <li>pastStates: List of states of the horse races that have already occurred.</li>
     *              <li>startTimeFrom: The start time for filtering past races.</li>
     *              <li>startTimeTo: The end time for filtering future races.</li>
     *              <li>futureState: The state of the horse races that are scheduled for the future.</li>
     *              <li>raceDateTime: The date and time of the race to compare against.</li>
     *              <li>horseRaceUuidsToExclude: Horse Race uuids to exclude from search</li>
     *            </ul>
     * @return a list of {@link HorseEntity} objects that match the specified criteria.
     */
    @Query("SELECT h FROM HorseEntity h " +
            "JOIN h.participations p " +
            "WHERE h.uuid IN :#{#dto.uuids} " +
            "AND ((p.horseRace.state IN :#{#dto.pastStates} " +
            "AND p.horseRace.startTime <= :#{#dto.raceDateTime} " +
            "AND p.horseRace.startTime >= :#{#dto.startTimeFrom}) " +
            "OR (p.horseRace.state = :#{#dto.futureState} " +
            "AND p.horseRace.startTime >= :#{#dto.raceDateTime} " +
            "AND p.horseRace.startTime <= :#{#dto.startTimeTo}))" +
            "AND p.horseRace.uuid NOT IN :#{#dto.horseRaceUuidsToExclude}")
    List<HorseEntity> findByUuidsWithRaceInStatesBetweenDates(FindByUuidsStateAndDatesQuery dto);

}