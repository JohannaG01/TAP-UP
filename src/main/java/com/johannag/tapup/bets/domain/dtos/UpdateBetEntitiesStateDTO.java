package com.johannag.tapup.bets.domain.dtos;

import com.johannag.tapup.bets.domain.models.BetModelState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Value
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UpdateBetEntitiesStateDTO {
    Collection<UUID> betUuid;
    BetModelState state;
}
