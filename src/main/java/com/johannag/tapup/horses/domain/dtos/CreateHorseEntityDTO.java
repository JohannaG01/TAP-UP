package com.johannag.tapup.horses.domain.dtos;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateHorseEntityDTO {
    UUID uuid;
    String code;
    String name;
    String breed;
    LocalDate birthDate;
    SexDTO sex;
    String color;
    HorseModelState state;
}
