package com.johannag.tapup.horses.infrastructure.db.dtos;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class FindByUuidStatesAndDatesDTO {
    List<UUID> uuids;
    LocalDateTime pastDateTime;
    LocalDateTime futureDateTime;
    LocalDateTime raceDateTime;
    List<UUID> horseRaceUuidsToExclude;
}
