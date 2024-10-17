package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.domain.models.BetModel;

public interface BetService {

    /**
     * Creates a new {@link BetModel} instance from the provided {@link CreateBetDTO}.
     *
     * @param dto the {@link CreateBetDTO} containing the details for the new bet
     * @return a {@link BetModel} representing the newly created bet
     */
    BetModel create(CreateBetDTO dto);
}
