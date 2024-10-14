package com.johannag.tapup.horses.infrastructure.db.dtos;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class FindByUuidsStateAndDatesQuery {
    List<UUID> uuids;
    List<HorseRaceEntityState> pastStates;
    LocalDateTime startTimeFrom;
    HorseRaceEntityState futureState;
    LocalDateTime startTimeTo;
    LocalDateTime raceDateTime;
    List<UUID> horseRaceUuidsToExclude;
}
