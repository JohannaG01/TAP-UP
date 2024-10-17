package com.johannag.tapup.bets.domain.mappers;

import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;

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
     * @param dto          the DTO containing the details of the bet to be converted
     * @param user         the user associated with the bet
     * @param participant   the participant involved in the bet
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
}
