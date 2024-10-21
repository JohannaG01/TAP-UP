package com.johannag.tapup.horses.presentation.schemas;

import com.johannag.tapup.globals.presentation.dtos.responses.PageResponse;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Pageable response containing horses")
public class PageHorseResponseDTO extends PageResponse {
    @Schema(description = "List of horse responses")
    List<HorseResponseDTO> content;
}