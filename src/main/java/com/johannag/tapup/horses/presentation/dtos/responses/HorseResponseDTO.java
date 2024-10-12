package com.johannag.tapup.horses.presentation.dtos.responses;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class HorseResponseDTO {
    UUID uuid;
    @Schema( example = "1312313")
    String code;
    @Schema( example = "Pancho")
    String name;
    @Schema( example = "Arabic")
    String breed;
    LocalDate birthDate;
    SexDTO sex;
    @Schema( example = "Black")
    String color;
    HorseStateDTO state;
}
