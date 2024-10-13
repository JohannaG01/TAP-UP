package com.johannag.tapup.horses.presentation.dtos.query;

import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.BindParam;

import java.util.HashSet;
import java.util.Set;

@Value
@Builder(builderClassName = "Builder")
public class FindHorsesQuery {
    @Min(value = 1, message = "Size must be at least 1")
    Integer size;
    @Min(value = 0, message = "Page must be at least 0")
    Integer page;
    @Nullable
    Set<HorseStateDTO> states;

    public FindHorsesQuery(@Nullable Integer size, @Nullable Integer page,
                           @Nullable @BindParam("state") Set<HorseStateDTO> states) {
        this.size = (size != null) ? size : 10;
        this.page = (page != null) ? page : 0;
        this.states = states != null ? states : new HashSet<>();
    }
}
