package com.johannag.tapup.bets.presentation.mappers;

import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;

public interface BetPresentationMapper {

    /**
     * Converts a {@link BetModel} instance into a {@link BetResponseDTO}.
     *
     * @param model the {@link BetModel} instance to convert
     * @return a {@link BetResponseDTO} representation of the provided {@link BetModel}
     */
    BetResponseDTO toResponseDTO(BetModel model);
}
