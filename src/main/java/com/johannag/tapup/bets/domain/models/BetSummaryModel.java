package com.johannag.tapup.bets.domain.models;

import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Data
@Builder(builderClassName = "Builder")
public class BetSummaryModel {
    HorseModel horse;
    Integer totalBets;
    BigDecimal totalAmountWagered;
    BigDecimal totalPayouts;
    Double odds;

    public void calculateOdds(Integer maxTotalBets, Double minOdds) {
        this.odds = minOdds + (maxTotalBets - this.getTotalBets()) / (maxTotalBets + 1.0);;
    }
}
