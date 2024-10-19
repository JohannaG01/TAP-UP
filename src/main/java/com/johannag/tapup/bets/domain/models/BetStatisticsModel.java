package com.johannag.tapup.bets.domain.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
public class BetStatisticsModel {
    private Long totalBets;
    private BigDecimal totalAmountWagered;
    private BigDecimal totalPayouts;
    private List<BetSummaryModel> bets;

    public Optional<Double> getOdds(UUID horseUuid){
        return bets.stream()
                .filter(summary -> summary.getHorseUuid().equals(horseUuid))
                .map(BetSummaryModel::getOdds)
                .findFirst();
    }

    public Optional<Long> getTotalBetsByHorseUuid(UUID horseUuid){
        return bets.stream()
                .filter(summary -> summary.getHorseUuid().equals(horseUuid))
                .map(BetSummaryModel::getTotalBets)
                .findFirst();
    }
}
