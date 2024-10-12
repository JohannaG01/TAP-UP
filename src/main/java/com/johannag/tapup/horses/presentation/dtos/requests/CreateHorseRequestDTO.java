package com.johannag.tapup.horses.presentation.dtos.requests;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "Builder")
public class CreateHorseRequestDTO {
    @Null
    @NotBlank
    @Schema( example = "234234")
    String code;
    @Null
    @NotBlank
    @Schema( example = "Pancho")
    String name;
    @Null
    @NotBlank
    @Schema( example = "Arabic")
    String breed;
    @Null
    @PastOrPresent
    LocalDate birthDate;
    @Null
    SexDTO sex;
    @Null
    @NotBlank
    @Schema( example = "Black")
    String color;
    @Null
    HorseStateDTO state;
}
