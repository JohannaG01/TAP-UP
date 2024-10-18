package com.johannag.tapup.bets.presentation.dtos.responses;

import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(builderClassName = "Builder")
public class BetSummaryDTO {
    HorseResponseDTO horse;
    Integer totalBets;
    BigDecimal totalAmountWagered;
    BigDecimal totalPayouts;
    Double odds;
}
