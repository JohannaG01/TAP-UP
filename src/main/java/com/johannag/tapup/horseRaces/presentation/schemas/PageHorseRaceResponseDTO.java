package com.johannag.tapup.horseRaces.presentation.schemas;

import com.johannag.tapup.globals.presentation.dtos.responses.PageResponse;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper=true)
@Schema(description = "Pageable response containing horse races")
public class PageHorseRaceResponseDTO extends PageResponse {
    @Schema(description = "List of horse race responses")
    List<HorseRaceResponseDTO> content;
}