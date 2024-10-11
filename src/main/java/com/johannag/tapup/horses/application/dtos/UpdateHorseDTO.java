package com.johannag.tapup.horses.application.dtos;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.horses.application.exceptions.InvalidHorseStateException;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UpdateHorseDTO {
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

    public boolean willTemporallyInactivateHorse(){
        return this.state != null && this.state == HorseModelState.TEMPORARILY_INACTIVE;
    }

    public void validate(){
        if (state == HorseModelState.INACTIVE){
            throw new InvalidHorseStateException(state);
        }
    }
}
