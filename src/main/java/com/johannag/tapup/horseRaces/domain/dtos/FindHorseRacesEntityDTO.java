package com.johannag.tapup.horseRaces.domain.dtos;

import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.presentation.dtos.HorseRaceStateDTO;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class FindHorseRacesEntityDTO {
    Integer size;

    Integer page;

    @Nullable
    LocalDateTime startTimeFrom;

    @Nullable
    LocalDateTime startTimeTo;

    List<HorseRaceModelState> states;

    @Nullable
    UUID horseUuid;

    @Nullable
    String horseCode;

    @Nullable
    String horseName;

    @Nullable
    String horseBreed;
}
