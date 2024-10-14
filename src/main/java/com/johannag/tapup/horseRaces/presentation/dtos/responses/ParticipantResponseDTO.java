package com.johannag.tapup.horseRaces.presentation.dtos.responses;

import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponseDTO {
    @NotNull
    private UUID uuid;

    @Nullable
    @Schema(example = "1")
    private Integer placement;

    @Nullable
    @Schema(example = "2:31:54")
    private LocalTime time;

    @NotNull
    private HorseResponseDTO horse;
}
