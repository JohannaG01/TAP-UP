package com.johannag.tapup.bets.infrastructure.db.projections;

import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class BetSummaryProjection {
    private HorseEntity horse;
    private Long totalBets;
    private BigDecimal totalWagered;
    private BigDecimal paidBaseAmount;
}
