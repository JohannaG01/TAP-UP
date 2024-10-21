package com.johannag.tapup.bets.presentation.schemas;

import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;
import com.johannag.tapup.globals.presentation.dtos.responses.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Pageable response containing bets")
public class PageBetResponseDTO extends PageResponse {
    @Schema(description = "List of bet responses")
    List<BetResponseDTO> content;
}