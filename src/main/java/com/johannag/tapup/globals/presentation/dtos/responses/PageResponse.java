package com.johannag.tapup.globals.presentation.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public abstract class PageResponse {
    @Schema(description = "Total number of elements available")
    long totalElements;

    @Schema(description = "Total number of pages available")
    int totalPages;

    @Schema(description = "Current page number")
    int number;

    @Schema(description = "Size of the page")
    int size;

}
