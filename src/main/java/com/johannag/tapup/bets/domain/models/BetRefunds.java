package com.johannag.tapup.bets.domain.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(builderClassName = "Builder")
public class BetRefunds {
    private Long totalBets;
    private Long totalRefunds;
    private BigDecimal totalAmount;

    @Override
    public String toString() {
        return "[" +
                "  TotalBets=" + totalBets +
                ", TotalRefunds=" + totalRefunds +
                ", TotalAmount=" + totalAmount +
                ']';
    }
}
