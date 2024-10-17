package com.johannag.tapup.bets.presentation.schemas;

import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Value
@Schema(description = "Pageable response containing bets")
public class PageBetResponseDTO {
    @Schema(description = "List of bet responses")
    List<BetResponseDTO> content;

    @Schema(description = "Total number of elements available")
    long totalElements;

    @Schema(description = "Total number of pages available")
    int totalPages;

    @Schema(description = "Current page number")
    int number;

    @Schema(description = "Size of the page")
    int size;
}