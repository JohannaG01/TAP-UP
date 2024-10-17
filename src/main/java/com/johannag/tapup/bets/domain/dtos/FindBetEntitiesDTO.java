package com.johannag.tapup.bets.domain.dtos;

import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class FindBetEntitiesDTO {
    UUID userUuid;
    Integer size;
    Integer page;
    Set<BetModelState> betStates;
    Set<HorseRaceModelState> horseRaceStates;
    @Nullable
    BigDecimal minAmount;
    @Nullable
    BigDecimal maxAmount;
    @Nullable
    Integer placement;
    @Nullable
    UUID horseUuid;
    @Nullable
    UUID horseRaceUuid;
    @Nullable
    LocalDateTime startTimeFrom;
    @Nullable
    LocalDateTime startTimeTo;
}
