package com.johannag.tapup.horseRaces.presentation.dtos.queries;

import com.johannag.tapup.globals.presentation.validations.annotations.NullOrNotBlank;
import com.johannag.tapup.horseRaces.presentation.dtos.HorseRaceStateDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class FindHorseRacesQuery {

    @Min(value = 1, message = "Size must be at least 1")
    Integer size;

    @Min(value = 0, message = "Page must be at least 0")
    Integer page;

    @Nullable
    LocalDateTime startTimeFrom;

    @Nullable
    LocalDateTime startTimeTo;

    Set<HorseRaceStateDTO> states;

    @Nullable
    UUID horseUuid;

    @Nullable
    @NullOrNotBlank
    String horseCode;

    @Nullable
    @NullOrNotBlank
    String horseName;

    @Nullable
    @NullOrNotBlank
    String horseBreed;

    public FindHorseRacesQuery(@Nullable Integer size,
                               @Nullable Integer page,
                               @Nullable LocalDateTime startTimeFrom,
                               @Nullable LocalDateTime startTimeTo,
                               @Nullable @BindParam("state") Set<HorseRaceStateDTO> states,
                               @Nullable UUID horseUuid,
                               @Nullable String horseCode,
                               @Nullable String horseName,
                               @Nullable String horseBreed) {
        this.size = (size != null) ? size : 10;
        this.page = (page != null) ? page : 0;
        this.startTimeFrom = startTimeFrom;
        this.startTimeTo = startTimeTo;
        this.states = states != null ? states : new HashSet<>();
        this.horseUuid = horseUuid;
        this.horseCode = horseCode;
        this.horseName = horseName;
        this.horseBreed = horseBreed;
    }

    @Hidden
    @AssertFalse(message = "startTimeFrom must not be after startTimeTo")
    public boolean isStartDateRangeInvalid() {
        return startTimeFrom != null && startTimeTo != null && startTimeFrom.isAfter(startTimeTo);
    }
}
