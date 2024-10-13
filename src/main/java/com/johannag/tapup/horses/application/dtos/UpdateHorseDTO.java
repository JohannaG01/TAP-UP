package com.johannag.tapup.horses.application.dtos;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.horses.application.exceptions.InvalidHorseStateException;
import com.johannag.tapup.horses.domain.models.HorseModelState;
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
    SexModel sex;
    @Nullable
    String color;
    @Nullable
    HorseModelState state;

    public boolean isStateTemporallyInactive() {
        return this.state != null && this.state == HorseModelState.TEMPORALLY_INACTIVE;
    }

    public void validate() {
        if (state == HorseModelState.INACTIVE) {
            throw new InvalidHorseStateException(state);
        }
    }
}
