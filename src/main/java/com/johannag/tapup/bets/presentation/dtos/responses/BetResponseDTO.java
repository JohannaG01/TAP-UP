package com.johannag.tapup.bets.presentation.dtos.responses;

import com.johannag.tapup.bets.presentation.dtos.BetStateDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.ParticipantResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class BetResponseDTO {

    @NotNull
    UUID uuid;
    @NotNull
    BigDecimal amount;
    @NotNull
    BetStateDTO state;
    @NotNull
    ParticipantResponseDTO participant;
}
