package com.johannag.tapup.bets.domain.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
public class BetStatisticsModel {
    private Long totalBets;
    private BigDecimal totalAmountWagered;
    private BigDecimal totalPayouts;
    private List<BetSummaryModel> bets;

    /**
     * Retrieves the odds for a specific horse identified by its UUID.
     *
     * @param horseUuid the UUID of the horse for which to retrieve the odds
     * @return the odds associated with the specified horse
     * @throws NoSuchElementException if no odds are found for the given horse UUID
     */
    public Double getOddsByHorseUuid(UUID horseUuid) throws NoSuchElementException {
        return bets.stream()
                .filter(summary -> summary.getHorseUuid().equals(horseUuid))
                .map(BetSummaryModel::getOdds)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cannot find odds by horseUuid: " + horseUuid));
    }


    /**
     * Retrieves the total number of bets placed for a specific horse identified by its UUID.
     *
     * @param horseUuid the UUID of the horse for which to retrieve the total number of bets
     * @return the total number of bets associated with the specified horse
     * @throws NoSuchElementException if no total bets are found for the given horse UUID
     */
    public Long getTotalBetsByHorseUuid(UUID horseUuid) throws NoSuchElementException {
        return bets.stream()
                .filter(summary -> summary.getHorseUuid().equals(horseUuid))
                .map(BetSummaryModel::getTotalBets)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cannot find totalBets by horseUuid: " + horseUuid));
    }
}
