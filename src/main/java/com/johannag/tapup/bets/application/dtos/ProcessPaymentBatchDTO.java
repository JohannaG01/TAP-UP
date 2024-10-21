package com.johannag.tapup.bets.application.dtos;

import com.johannag.tapup.bets.domain.models.BetModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
@AllArgsConstructor
public class ProcessPaymentBatchDTO {
    UUID horseRaceUuid;
    Page<BetModel> bets;
    double odds;

    public int currentPage() {
        return bets.getNumber();
    }
}
