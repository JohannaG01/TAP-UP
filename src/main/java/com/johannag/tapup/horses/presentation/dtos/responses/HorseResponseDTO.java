package com.johannag.tapup.horses.presentation.dtos.responses;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class HorseResponseDTO {

    @NotNull
    UUID uuid;

    @Schema(example = "1312313")
    @NotNull
    String code;

    @Schema(example = "Pancho")
    @NotNull
    String name;

    @Schema(example = "Arabic")
    @NotNull
    String breed;

    @NotNull
    LocalDate birthDate;

    @NotNull
    SexDTO sex;

    @Schema(example = "Black")
    @NotNull
    String color;

    @NotNull
    HorseStateDTO state;
}
