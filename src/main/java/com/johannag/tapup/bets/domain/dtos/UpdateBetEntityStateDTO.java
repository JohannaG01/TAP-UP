package com.johannag.tapup.bets.domain.dtos;

import com.johannag.tapup.bets.domain.models.BetModelState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UpdateBetEntityStateDTO {
    UUID betUuid;
    BetModelState state;
}
