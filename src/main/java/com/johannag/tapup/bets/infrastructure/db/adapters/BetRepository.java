package com.johannag.tapup.bets.infrastructure.db.adapters;

import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BetRepository {

    /**
     * Creates a new bet based on the provided {@link CreateBetEntityDTO}.
     *
     * @param dto the DTO containing the details for the new bet
     * @return a {@link BetModel} representing the created bet
     */
    BetModel create(CreateBetEntityDTO dto);

    /**
     * Retrieves a paginated list of {@link BetModel} entities based on the specified search criteria.
     *
     * <p>This method accepts a {@link FindBetEntitiesDTO} that contains various filters and pagination
     * parameters to find the appropriate bet entities in the database. It returns a {@link Page} of
     * {@link BetModel} instances that match the criteria provided.
     *
     * @param dto the {@link FindBetEntitiesDTO} containing the search criteria and pagination details
     * @return a {@link Page} containing a list of {@link BetModel} entities that match the specified criteria
     */
    Page<BetModel> findAll(FindBetEntitiesDTO dto);

    /**
     * Retrieves the details of bets associated with a specific horse race.
     *
     * @param horseRaceUuid The unique identifier of the horse race for which bet details are to be retrieved.
     *                      This parameter must not be null.
     * @return A list of {@link BetSummaryDTO} objects containing the details of bets for the specified horse race.
     * If no bets are found for the given horse race UUID, an empty list is returned.
     */
    List<BetSummaryDTO> findBetDetails(UUID horseRaceUuid);
}
