package com.johannag.tapup.horseRaces.presentation.dtos.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UpdateHorseRaceRequestDTO {

    @NotNull
    @Future
    private LocalDateTime startTime;
}
