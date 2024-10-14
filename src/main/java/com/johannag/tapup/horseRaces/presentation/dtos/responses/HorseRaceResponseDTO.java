package com.johannag.tapup.horseRaces.presentation.dtos.responses;

import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
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
    List<ParticipantResponseDTO> participants;
}
