package com.johannag.tapup.bets.application.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateBetDTO {
    UUID userUuid;
    UUID participantUuid;
    BigDecimal amount;
}
