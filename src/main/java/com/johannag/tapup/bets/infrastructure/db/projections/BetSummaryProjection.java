package com.johannag.tapup.bets.infrastructure.db.projections;

import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class BetSummaryProjection {
     private HorseEntity horse;
     private Long totalBets;
     private BigDecimal totalWagered;
     private BigDecimal paidBaseAmount;
}
