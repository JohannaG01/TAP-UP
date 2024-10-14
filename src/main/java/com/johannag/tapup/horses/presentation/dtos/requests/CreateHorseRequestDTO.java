package com.johannag.tapup.horses.presentation.dtos.requests;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseStateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "Builder")
public class CreateHorseRequestDTO {

    @NotBlank
    @Schema(example = "234234")
    String code;

    @NotBlank
    @Schema(example = "Pancho")
    String name;

    @NotBlank
    @Schema(example = "Arabic")
    String breed;

    @PastOrPresent
    @NotNull
    LocalDate birthDate;

    @NotNull
    SexDTO sex;

    @NotBlank
    @Schema(example = "Black")
    String color;

    @NotNull
    HorseStateDTO state;
}
