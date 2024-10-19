package com.johannag.tapup.bets.application.mappers;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.dtos.UpdateBetEntityStateDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.presentation.dtos.queries.FindBetsQuery;
import com.johannag.tapup.bets.presentation.dtos.requests.CreateBetRequestDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface BetApplicationMapper {
    /**
     * Converts a {@link CreateBetRequestDTO} to a {@link CreateBetDTO}.
     *
     * @param userUuid the unique identifier of the user associated with the bet
     * @param dto      the DTO containing the details for creating a bet
     * @return a {@link CreateBetDTO} object containing the user UUID and bet details
     */
    CreateBetDTO toCreateDTO(UUID userUuid, CreateBetRequestDTO dto);

    /**
     * Converts a {@link CreateBetDTO} to a {@link CreateBetEntityDTO}.
     *
     * @param dto the DTO containing the details of the bet to be converted
     * @return a {@link CreateBetEntityDTO} representing the converted entity
     */
    CreateBetEntityDTO toCreateEntityDTO(CreateBetDTO dto);

    /**
     * Converts the given {@link FindBetsQuery} object into a {@link FindBetsDTO} and assigns the provided user UUID.
     *
     * <p>This method transforms the provided {@link FindBetsQuery}, typically used for search criteria,
     * into a {@link FindBetsDTO}, while also assigning the user UUID to the result.
     *
     * <p>The conversion includes mapping search parameters like bet states, horse race states, amount range, and
     * date range,
     * and ensures the user's unique identifier is incorporated in the DTO for user-specific operations.
     *
     * @param userUuid the UUID of the user to be associated with the search query
     * @param query    the {@link FindBetsQuery} containing search parameters such as bet states, amount, and date
     *                 ranges
     * @return the created {@link FindBetsDTO} containing the search parameters and user UUID
     */
    FindBetsDTO toFindDTO(UUID userUuid, FindBetsQuery query);

    /**
     * Converts a {@link FindBetsDTO} into a {@link FindBetEntitiesDTO} to be used for querying bet entities.
     *
     * <p>This method maps the properties of the {@link FindBetsDTO} into a new {@link FindBetEntitiesDTO}
     * object, which can then be used for querying bet entities from the database. This may involve
     * transforming filters, pagination, and other search criteria.
     *
     * @param dto the {@link FindBetsDTO} containing the search criteria
     * @return a {@link FindBetEntitiesDTO} containing the transformed search criteria for querying entities
     */
    FindBetEntitiesDTO toFindEntitiesDTO(FindBetsDTO dto);

    /**
     * Converts a collection of {@link BetModel} objects into a list of {@link UpdateBetEntityStateDTO}
     * containing the unique identifiers (UUIDs) and the states of the bets.
     *
     * @param bets a collection of {@link BetModel} objects to be converted; must not be {@code null} or empty.
     * @return a list of {@link UpdateBetEntityStateDTO} representing the UUIDs and states of the provided bets.
     * @throws IllegalArgumentException if the provided collection of bets is {@code null} or empty.
     */
    List<UpdateBetEntityStateDTO> toUpdateEntityStateDTO(Collection<BetModel> bets);

}
