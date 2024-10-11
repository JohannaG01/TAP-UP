package com.johannag.tapup.horses.presentation.dtos.responses;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class HorseResponseDTO {
    UUID uuid;
    String code;
    String name;
    String breed;
    LocalDate birthDate;
    SexDTO sex;
    String color;
    HorseStateDTO state;
}
