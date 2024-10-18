package com.johannag.tapup.bets.infrastructure.db.repositories;

import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
import com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
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
     * @param spec the Specification that defines the criteria for retrieving the BetEntity objects.
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
    @Query("SELECT NEW com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection(p.horse, COUNT(b)) " +
            "FROM ParticipantEntity p " +
            "LEFT JOIN BetEntity b ON b.participant = p " +
            "WHERE p.horseRace.uuid = :horseRaceUuid " +
            "GROUP BY p.horse")
    List<BetSummaryProjection> findBetDetails(UUID horseRaceUuid);

    /**
     * Retrieves the total payouts for bets in specific states for each horse in a horse race.
     *
     * <p>This method calculates the total payouts by summing the amounts of bets that
     * are in the specified states for a given horse race. The results are returned as
     * an array of objects, where each array contains the horse UUID and the corresponding
     * total payout. If no bets exist in the specified states, the total payout will be zero.</p>
     *
     * @param horseRaceUuid the UUID of the horse race for which the payouts are to be retrieved.
     * @param states a list of BetEntityState representing the states of the bets to include in the calculation.
     * @return a list of Object arrays, where each array contains two elements:
     *         - The first element is the UUID of the horse (UUID).
     *         - The second element is the total payouts for that horse (BigDecimal).
     *         The list is ordered by horse UUID.
     */
    @Query("SELECT p.horse.uuid AS horseUuid, COALESCE(SUM(b.amount), 0) AS totalPayouts " +
            "FROM BetEntity b " +
            "JOIN b.participant p " +
            "WHERE b.state in :states AND p.horseRace.uuid = :horseRaceUuid " +
            "GROUP BY p.horse.uuid")
    List<Object[]> findAmountForBetsInState(UUID horseRaceUuid, List<BetEntityState> states);
}