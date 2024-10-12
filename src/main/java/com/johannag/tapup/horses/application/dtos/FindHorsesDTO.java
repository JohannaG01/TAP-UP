package com.johannag.tapup.horses.application.dtos;

import com.johannag.tapup.horses.domain.models.HorseModelState;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value
@Builder(builderClassName = "Builder")
public class FindHorsesDTO {
    int size;
    int page;
    List<HorseModelState> states;
}
