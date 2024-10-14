package com.johannag.tapup.horseRaces.application.dtos;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UpdateHorseRaceDTO {
    UUID horseRaceUuid;
    LocalDateTime startTime;
}
