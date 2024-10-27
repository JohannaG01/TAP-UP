package com.johannag.tapup.bets.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetRefundsCache {

    @Builder.Default
    private long totalRefunds = 0;

    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    public void addAmount(BigDecimal amount) {
        this.totalAmount = this.totalAmount.add(amount);
    }

    public void addRefunds(long refunds) {
        this.totalRefunds = this.totalRefunds + refunds;
    }
}
