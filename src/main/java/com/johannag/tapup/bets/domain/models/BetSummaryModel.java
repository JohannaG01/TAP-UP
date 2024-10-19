package com.johannag.tapup.bets.domain.models;

import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class BetSummaryModel {
    private HorseModel horse;
    private Long totalBets;
    private BigDecimal totalAmountWagered;
    private BigDecimal totalPayouts;
    private Double odds;

    public BetSummaryModel(BetSummaryDTO betSummaryDTO, int scale) {
        this.horse = betSummaryDTO.getHorse();
        this.totalBets = betSummaryDTO.getTotalBets();
        this.totalAmountWagered = betSummaryDTO.getTotalWagered().setScale(scale, RoundingMode.HALF_UP);
    }

    public UUID getHorseUuid(){
        return horse.getUuid();
    }
}
