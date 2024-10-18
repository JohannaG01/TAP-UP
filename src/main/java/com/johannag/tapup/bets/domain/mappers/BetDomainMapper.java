package com.johannag.tapup.bets.domain.mappers;

import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
import com.johannag.tapup.bets.infrastructure.db.projections.BetSummaryProjection;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;

import java.util.Collection;
import java.util.List;

public interface BetDomainMapper {

    /**
     * Converts a {@link BetEntity} to a {@link BetModel} without establishing bidirectional relationships.
     *
     * @param entity the entity containing the details of the bet to be converted
     * @return a {@link BetModel} representing the converted bet
     */
    BetModel toModel(BetEntity entity);

    /**
     * Converts a {@link CreateBetEntityDTO} to a {@link BetEntity} using the specified user and participant.
     *
     * @param dto         the DTO containing the details of the bet to be converted
     * @param user        the user associated with the bet
     * @param participant the participant involved in the bet
     * @return a {@link BetEntity} representing the created bet entity
     */
    BetEntity toEntity(CreateBetEntityDTO dto, UserEntity user, ParticipantEntity participant);

    /**
     * Converts a {@link BetModelState} to a {@link BetEntityState}.
     *
     * <p>This method is used to convert the state representation of a bet model into
     * its corresponding entity state, allowing for persistence in the database.</p>
     *
     * @param state the state of the bet model to be converted
     * @return a {@link BetEntityState} representing the converted entity state
     */
    BetEntityState toEntity(BetModelState state);

    /**
     * Converts a collection of {@link BetModelState} to a list of {@link BetEntityState}.
     *
     * <p>This method takes a collection of bet model states and maps them to their corresponding
     * entity states, which can be used for persistence in the database.</p>
     *
     * @param states the collection of {@link BetModelState} objects to convert
     * @return a list of {@link BetEntityState} representing the converted states
     * or an empty list if the input collection is null or empty
     */
    List<BetEntityState> toEntity(Collection<BetModelState> states);

    /**
     * Converts lists of bet summary projections, payouts, and amounts wagered into a list of BetSummaryModel objects.
     *
     * <p>This method maps the provided projections and their associated payouts and amounts wagered
     * into BetSummaryModel instances. Each BetSummaryModel will contain information about the horse,
     * the total payouts, and the total amounts wagered for the corresponding bets.</p>
     *
     * @param projections a list of BetSummaryProjection objects containing the summary of bets per horse.
     * @param payouts a list of Object arrays where each array contains the horse UUID and the total payouts
     *                for that horse. The order should match the order of the projections.
     * @param amountWagered a list of Object arrays where each array contains the horse UUID and the total
     *                      amount wagered for that horse. The order should match the order of the projections.
     * @return a list of BetSummaryModel objects that encapsulate the summarized betting information,
     *         including horse details, total payouts, and amounts wagered.
     */
    List<BetSummaryModel> toModel(List<BetSummaryProjection> projections, List<Object[]> payouts,
                                  List<Object[]> amountWagered);
}
