package com.johannag.tapup.bets.domain.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(builderClassName = "Builder")
public class BetPayouts {
    private Long totalBets;
    private Long totalWinningBets;
    private Long totalPayouts;
    private BigDecimal totalAmount;

    @Override
    public String toString() {
        return " [TotalBets=" + totalBets +
                ", TotalWinningBets=" + totalWinningBets +
                ", TotalPayouts=" + totalPayouts +
                ", TotalAmount=" + totalAmount +
                ']';
    }
}
