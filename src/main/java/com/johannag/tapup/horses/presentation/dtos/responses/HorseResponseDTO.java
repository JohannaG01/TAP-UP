package com.johannag.tapup.horses.presentation.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

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
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    HorseStateDTO state;
}
