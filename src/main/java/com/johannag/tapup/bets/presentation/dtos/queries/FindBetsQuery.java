package com.johannag.tapup.bets.presentation.dtos.queries;

import com.johannag.tapup.bets.presentation.dtos.BetStateDTO;
import com.johannag.tapup.globals.presentation.dtos.query.PageQuery;
import com.johannag.tapup.horseRaces.presentation.dtos.HorseRaceStateDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.AssertFalse;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.BindParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Value
@SuperBuilder
public class FindBetsQuery extends PageQuery {

    Set<BetStateDTO> betStates;

    Set<HorseRaceStateDTO> horseRaceStates;

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

    public FindBetsQuery(Integer size,
                         Integer page,
                         @Nullable @BindParam("betState") Set<BetStateDTO> betStates,
                         @Nullable @BindParam("horseRaceState") Set<HorseRaceStateDTO> horseRaceStates,
                         @Nullable BigDecimal minAmount,
                         @Nullable BigDecimal maxAmount,
                         @Nullable Integer placement,
                         @Nullable UUID horseUuid,
                         @Nullable UUID horseRaceUuid,
                         @Nullable LocalDateTime startTimeFrom,
                         @Nullable LocalDateTime startTimeTo) {
        super(size, page);
        this.betStates = betStates != null ? betStates : new HashSet<>();
        this.horseRaceStates = horseRaceStates != null ? horseRaceStates : new HashSet<>();
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.placement = placement;
        this.horseUuid = horseUuid;
        this.horseRaceUuid = horseRaceUuid;
        this.startTimeFrom = startTimeFrom;
        this.startTimeTo = startTimeTo;
    }

    @Hidden
    @AssertFalse(message = "startTimeFrom must not be after startTimeTo")
    public boolean isStartDateRangeInvalid() {
        return startTimeFrom != null && startTimeTo != null && startTimeFrom.isAfter(startTimeTo);
    }

    @Hidden
    @AssertFalse(message = "minAmount must be lower or equal than maxAmount")
    public boolean isAmountRangeInvalid() {
        return minAmount != null && maxAmount != null && minAmount.compareTo(maxAmount) > 0;
    }
}
