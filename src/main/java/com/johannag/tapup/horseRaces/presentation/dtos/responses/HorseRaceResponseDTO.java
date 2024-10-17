package com.johannag.tapup.horseRaces.presentation.dtos.responses;

import com.fasterxml.jackson.annotation.JsonView;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.views.ParticipantView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class HorseRaceResponseDTO {

    @NotNull
    UUID uuid;

    @NotNull
    LocalDateTime startTime;

    @Nullable
    LocalDateTime endTime;

    @NotNull
    HorseRaceModelState state;

    @NotEmpty
    @JsonView(ParticipantView.ParticipantWithoutRace.class)
    List<ParticipantResponseDTO> participants;
}
