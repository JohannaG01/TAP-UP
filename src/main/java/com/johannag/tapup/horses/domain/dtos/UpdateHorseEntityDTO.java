package com.johannag.tapup.horses.domain.dtos;


import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UpdateHorseEntityDTO {
    UUID uuid;
    @Nullable
    String name;
    @Nullable
    String breed;
    @Nullable
    LocalDate birthDate;
    @Nullable
    SexDTO sex;
    @Nullable
    String color;
    @Nullable
    HorseModelState state;
}
