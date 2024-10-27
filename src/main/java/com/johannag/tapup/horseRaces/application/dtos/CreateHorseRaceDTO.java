package com.johannag.tapup.horseRaces.application.dtos;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateHorseRaceDTO {
    LocalDateTime startTime;
    List<UUID> horsesUuids;
}
