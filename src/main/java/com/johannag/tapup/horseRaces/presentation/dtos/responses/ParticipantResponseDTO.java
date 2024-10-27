package com.johannag.tapup.horseRaces.presentation.dtos.responses;

import com.fasterxml.jackson.annotation.JsonView;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.views.ParticipantView;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class ParticipantResponseDTO {

    @NotNull
    UUID uuid;

    @Nullable
    @Schema(example = "1")
    Integer placement;

    @Nullable
    LocalTime time;

    @NotNull
    HorseResponseDTO horse;

    @NotNull
    @JsonView(ParticipantView.ParticipantWithRace.class)
    HorseRaceResponseDTO horseRace;
}
