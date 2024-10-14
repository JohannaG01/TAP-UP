package com.johannag.tapup.horses.presentation.dtos.responses;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
public class HorseResponseDTO {

    @NotNull
    private UUID uuid;

    @Schema(example = "1312313")
    @NotNull
    private String code;

    @Schema(example = "Pancho")
    @NotNull
    private String name;

    @Schema(example = "Arabic")
    @NotNull
    private String breed;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private SexDTO sex;

    @Schema(example = "Black")
    @NotNull
    private String color;

    @NotNull
    private HorseStateDTO state;
}
