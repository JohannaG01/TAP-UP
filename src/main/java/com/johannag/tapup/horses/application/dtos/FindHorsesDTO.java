package com.johannag.tapup.horses.application.dtos;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.globals.presentation.validations.annotations.NullOrNotBlank;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@Builder(builderClassName = "Builder")
public class FindHorsesDTO {
    int size;
    int page;
    List<HorseModelState> states;
    @Nullable
    String name;
    @Nullable
    String code;
    @Nullable
    String breed;
    @Nullable
    SexModel sex;
    @Nullable
    LocalDate birthDateFrom;
    @Nullable
    LocalDate birthDateTo;
    @Nullable
    String color;
}
