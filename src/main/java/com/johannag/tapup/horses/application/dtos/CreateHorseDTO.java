package com.johannag.tapup.horses.application.dtos;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(builderClassName = "Builder")
public class CreateHorseDTO {
    String code;
    String name;
    String breed;
    LocalDate birthDate;
    SexModel sex;
    String color;
}
