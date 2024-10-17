package com.johannag.tapup.bets.presentation.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateBetRequestDTO {

    @NotNull
    UUID participantUuid;

    @Positive
    BigDecimal amount;
}
