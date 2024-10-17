package com.johannag.tapup.horseRaces.presentation.schemas;

import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Value
@Schema(description = "Pageable response containing horse races")
public class PageHorseRaceResponseDTO {
    @Schema(description = "List of horse race responses")
    List<HorseRaceResponseDTO> content;

    @Schema(description = "Total number of elements available")
    long totalElements;

    @Schema(description = "Total number of pages available")
    int totalPages;

    @Schema(description = "Current page number")
    int number;

    @Schema(description = "Size of the page")
    int size;
}