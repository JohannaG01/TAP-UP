package com.johannag.tapup.bets.domain.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetPayoutsCache {
    private long totalPayouts = 0;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    public void addAmount(BigDecimal amount) {
        this.totalAmount = this.totalAmount.add(amount);
    }

    public void addPayouts(long payouts) {
        this.totalPayouts = this.totalPayouts + payouts;
    }
}
