package com.johannag.tapup.horses.presentation.dtos.requests;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
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
    String code;
    @NotBlank
    String name;
    @NotBlank
    String breed;
    @NotNull
    @PastOrPresent
    LocalDate birthDate;
    @NotNull
    SexDTO sex;
    @NotBlank
    String color;
}
