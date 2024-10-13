package com.johannag.tapup.horses.application.dtos;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
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
