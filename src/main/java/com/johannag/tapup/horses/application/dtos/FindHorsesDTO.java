package com.johannag.tapup.horses.application.dtos;

import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

@Data
@Builder(builderClassName = "Builder")
public class FindHorsesDTO {
    int size;
    int page;
    List<HorseModelState> states;
}
