package com.johannag.tapup.horses.presentation.dtos.queries;

import com.johannag.tapup.globals.presentation.dtos.SexDTO;
import com.johannag.tapup.globals.presentation.dtos.query.PageQuery;
import com.johannag.tapup.globals.presentation.validations.annotations.NullOrNotBlank;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseStateDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FindHorsesQuery extends PageQuery {

    Set<HorseStateDTO> states;

    @Nullable
    @NullOrNotBlank
    String name;

    @Nullable
    @NullOrNotBlank
    String code;

    @Nullable
    @NullOrNotBlank
    String breed;

    @Nullable
    SexDTO sex;

    @Nullable
    @PastOrPresent
    LocalDate birthDateFrom;

    @Nullable
    @PastOrPresent
    LocalDate birthDateTo;

    @Nullable
    @NullOrNotBlank
    String color;

    public FindHorsesQuery(@Nullable Integer size,
                           @Nullable Integer page,
                           @Nullable @BindParam("state") Set<HorseStateDTO> states,
                           @Nullable String name,
                           @Nullable String code,
                           @Nullable String breed,
                           @Nullable SexDTO sex,
                           @Nullable LocalDate birthDateFrom,
                           @Nullable LocalDate birthDateTo,
                           @Nullable String color) {
        super(size, page);
        this.states = states != null ? states : new HashSet<>();
        this.name = name;
        this.code = code;
        this.breed = breed;
        this.sex = sex;
        this.birthDateFrom = birthDateFrom;
        this.birthDateTo = birthDateTo;
        this.color = color;
    }

    @Hidden
    @AssertFalse(message = "birthDateFrom must not be after birthDateTo")
    public boolean isBirthDateRangeInvalid() {
        if (birthDateFrom == null || birthDateTo == null) {
            return false;
        }
        return birthDateFrom.isAfter(birthDateTo);
    }

}
