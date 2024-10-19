package com.johannag.tapup.bets.domain.dtos;

import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(builderClassName = "Builder")
public class BetSummaryDTO {
    HorseModel horse;
    Long totalBets;
    BigDecimal totalWagered;
    BigDecimal paidBaseAmount;

}
