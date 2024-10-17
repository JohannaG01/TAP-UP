package com.johannag.tapup.bets.application.mappers;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.presentation.dtos.requests.CreateBetRequestDTO;

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
}
