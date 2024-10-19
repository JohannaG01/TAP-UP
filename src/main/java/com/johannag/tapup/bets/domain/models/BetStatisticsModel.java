package com.johannag.tapup.bets.domain.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder(builderClassName = "Builder")
public class BetStatisticsModel {
    private Long totalBets;
    private BigDecimal totalAmountWagered;
    private BigDecimal totalPayouts;
    private List<BetSummaryModel> bets;
}
