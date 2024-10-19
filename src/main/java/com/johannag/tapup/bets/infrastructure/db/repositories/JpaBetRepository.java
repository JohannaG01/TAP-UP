package com.johannag.tapup.bets.infrastructure.db.repositories;

import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

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
}