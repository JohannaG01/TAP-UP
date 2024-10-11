package com.johannag.tapup.horses.presentation.dtos.requests;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    String code;
    @Null
    @NotBlank
    String name;
    @Null
    @NotBlank
    String breed;
    @Null
    @PastOrPresent
    LocalDate birthDate;
    @Null
    SexDTO sex;
    @Null
    @NotBlank
    String color;
    @Null
    HorseStateDTO state;
}
