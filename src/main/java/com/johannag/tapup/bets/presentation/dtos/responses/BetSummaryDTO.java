package com.johannag.tapup.bets.presentation.dtos.responses;

import com.fasterxml.jackson.annotation.JsonView;
import com.johannag.tapup.bets.presentation.dtos.responses.views.BetSummaryView;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(builderClassName = "Builder")
public class BetSummaryDTO {
    @NotNull
    HorseResponseDTO horse;

    @NotNull
    Long totalBets;

    @NotNull
    @JsonView(BetSummaryView.Detailed.class)
    BigDecimal totalAmountWagered;

    @NotNull
    @JsonView(BetSummaryView.Detailed.class)
    BigDecimal totalPayouts;

    @NotNull
    Double odds;
}
