package com.johannag.tapup.bets.infrastructure.db.adapters;

import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;

public interface BetRepository{

    /**
     * Creates a new bet based on the provided {@link CreateBetEntityDTO}.
     *
     * @param dto the DTO containing the details for the new bet
     * @return a {@link BetModel} representing the created bet
     */
    BetModel create(CreateBetEntityDTO dto);
}
