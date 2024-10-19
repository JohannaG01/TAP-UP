package com.johannag.tapup.bets.domain.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(builderClassName = "Builder")
public class BetPayoutsDTO {
    long totalPayouts;
    BigDecimal totalAmount;
}
