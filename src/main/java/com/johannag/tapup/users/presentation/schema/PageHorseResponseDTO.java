package com.johannag.tapup.users.presentation.schema;

import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Value
@Schema(description = "Pageable response containing horses")
public class PageHorseResponseDTO {
    @Schema(description = "List of horse responses")
     List<HorseResponseDTO> content;

    @Schema(description = "Total number of elements available")
     long totalElements;

    @Schema(description = "Total number of pages available")
     int totalPages;

    @Schema(description = "Current page number")
     int number;

    @Schema(description = "Size of the page")
     int size;
}