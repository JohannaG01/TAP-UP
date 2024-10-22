package com.johannag.tapup.globals.presentation.dtos.query;

import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
public abstract class PageQuery {
    @Min(value = 1, message = "Size must be at least 1")
    Integer size;

    @Min(value = 0, message = "Page must be at least 0")
    Integer page;

    public PageQuery(Integer size, Integer page) {
        this.size = (size != null) ? size : 10;
        this.page = (page != null) ? page : 0;
    }
}
