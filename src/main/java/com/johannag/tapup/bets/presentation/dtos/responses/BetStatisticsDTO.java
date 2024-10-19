package com.johannag.tapup.bets.presentation.dtos.responses;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(builderClassName = "Builder")
public class BetStatisticsDTO {
    @NotNull
    Long totalBets;

    @NotNull
    BigDecimal totalAmountWagered;

    @NotNull
    BigDecimal totalPayouts;

    @NotNull
    List<BetSummaryDTO> bets;
}
