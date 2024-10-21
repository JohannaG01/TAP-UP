package com.johannag.tapup.bets.infrastructure.db.repositories;

import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
import com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface JpaBetRepository extends JpaRepository<BetEntity, Long>, JpaSpecificationExecutor<BetEntity> {

    /**
     * Retrieves a paginated list of BetEntity objects based on the specified criteria.
     * <p>
     * This method utilizes an EntityGraph to fetch associated entities including user,
     * participant's horse race, and participant's horse in a single query to avoid
     * N+1 select problems and optimize performance.
     *
     * @param spec     the Specification that defines the criteria for retrieving the BetEntity objects.
     * @param pageable the pagination information including page number, page size, and sorting options.
     * @return a Page containing a list of BetEntity objects matching the specified criteria.
     */
    @NonNull
    @EntityGraph(attributePaths = {"user", "participant.horseRace", "participant.horse"})
    Page<BetEntity> findAll(Specification<BetEntity> spec, @NonNull Pageable pageable);


    /**
     * Retrieves a summary of bets placed in a horse race.
     *
     * @param horseRaceUuid the UUID of the horse race for which bet details are to be retrieved.
     * @return a list of BetSummaryProjection objects containing the summary of bets per horse.
     */
    @Query("SELECT new com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection(h, COUNT(b.id), " +
            "COALESCE(SUM(b.amount), 0), " +
            "COALESCE(SUM(CASE WHEN b.state = 'PAID' THEN b.amount ELSE 0 END), 0)) " +
            "FROM HorseEntity h " +
            "LEFT JOIN h.participations p " +
            "LEFT JOIN p.bets b " +
            "LEFT JOIN p.horseRace hr " +
            "WHERE hr.uuid = :horseRaceUuid " +
            "GROUP BY h.id")
    List<BetSummaryProjection> findBetDetails(UUID horseRaceUuid);

    /**
     * Finds all bets for a specific horse race, paginated and sorted by the user ID.
     * <p>
     * This method retrieves a paginated list of bets associated with a specific horse race UUID.
     * The results are sorted in ascending order by the user's ID.
     *
     * @param horseRaceUuid the UUID of the horse race for which bets are retrieved
     * @param page          the page number to retrieve, starting from 0
     * @param size          the size of the page (number of items per page)
     * @return a Page object containing a list of BetModel objects for the specified horse race,
     * sorted by the user ID
     */
    @EntityGraph(attributePaths = {"user", "participant.horseRace", "participant.horse"})
    Page<BetEntity> findByParticipant_HorseRace_UuidAndState(UUID horseRaceUuid, BetEntityState state,
                                                             Pageable pageable);

    /**
     * Retrieves a list of {@link BetEntity} objects by their unique identifiers (UUIDs)
     * while acquiring a pessimistic write lock on each entity to prevent concurrent
     * modifications.
     *
     * @param uuids a collection of unique identifiers of the {@link BetEntity} objects
     *              to be retrieved; must not be {@code null} or empty.
     * @return a list of {@link BetEntity} objects corresponding to the provided UUIDs,
     * or an empty list if no entities are found.
     */
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BetEntity b WHERE b.uuid IN :uuids")
    List<BetEntity> findAllByUuidForUpdate(Collection<UUID> uuids);

    /**
     * Updates the state of bets identified by their UUIDs to the specified state.
     *
     * @param betUuids a collection of {@link UUID} representing the unique identifiers of the bets to be updated
     * @param state    the {@link BetEntityState} representing the new state to set for the specified bets
     * @return the number of bets that were updated
     */
    @Modifying
    @Query("UPDATE BetEntity b SET b.state = :state WHERE b.uuid IN :betUuids")
    int updateBetStates(Collection<UUID> betUuids, BetEntityState state);

    /**
     * Counts the total number of bet entities associated with a specific horse race.
     * <p>
     * This method returns the total number of bet entities that are linked to the participants
     * in the horse race identified by the provided UUID.
     *
     * @param horseRaceUuid the UUID of the horse race for which to count bet entities
     * @return the total number of bet entities linked to the specified horse race
     */
    long countByParticipant_HorseRace_Uuid(UUID horseRaceUuid);

    /**
     * Counts the total number of bets for a given horse race and a specific participant placement.
     *
     * @param horseRaceUuid the UUID of the horse race to count bets for
     * @param placement     the placement of the participant to filter by
     * @return the number of bets that match the given horse race and participant placement
     */
    long countByParticipant_HorseRace_UuidAndParticipant_Placement(UUID horseRaceUuid, int placement);

}