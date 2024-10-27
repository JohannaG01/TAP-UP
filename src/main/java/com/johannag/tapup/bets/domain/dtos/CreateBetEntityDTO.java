package com.johannag.tapup.bets.domain.dtos;

import com.johannag.tapup.bets.domain.models.BetModelState;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateBetEntityDTO {
    UUID uuid;
    UUID userUuid;
    UUID participantUuid;
    BigDecimal amount;
    BetModelState state;
}
