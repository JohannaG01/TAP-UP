package com.johannag.tapup.horseRaces.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UpdateHorseRaceEntityDTO {
    UUID raceUuid;
    LocalDateTime startTime;
}
