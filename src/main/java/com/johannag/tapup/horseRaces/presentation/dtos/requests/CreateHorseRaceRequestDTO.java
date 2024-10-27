package com.johannag.tapup.horseRaces.presentation.dtos.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateHorseRaceRequestDTO {

    @Future
    @NotNull
    LocalDateTime startTime;

    @NotEmpty
    Set<UUID> horsesUuids;
}
