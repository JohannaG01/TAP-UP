package com.johannag.tapup.horses.presentation.dtos.requests;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.globals.presentation.validations.annotations.NullOrNotBlank;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "Builder")
public class UpdateHorseRequestDTO {
    @NullOrNotBlank
    String name;
    @NullOrNotBlank
    String breed;
    @PastOrPresent
    LocalDate birthDate;
    SexDTO sex;
    @NullOrNotBlank
    String color;
    HorseStateDTO state;
}
