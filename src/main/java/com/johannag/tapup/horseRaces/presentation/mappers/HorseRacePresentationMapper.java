package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import org.springframework.data.domain.Page;

public interface HorseRacePresentationMapper {

    /**
     * Converts a {@link HorseRaceModel} entity to a {@link HorseRaceResponseDTO}.
     *
     * @param model the {@link HorseRaceModel} entity to be converted
     * @return a {@link HorseRaceResponseDTO} representing the converted entity
     */
    HorseRaceResponseDTO toResponseDTO(HorseRaceModel model);

    /**
     * Converts a {@link HorseRaceModel} to a {@link HorseRaceResponseDTO} excluding participant information.
     *
     * @param model the {@link HorseRaceModel} to convert.
     * @return a {@link HorseRaceResponseDTO} without participant-related information.
     */
    HorseRaceResponseDTO toResponseDTOWithoutParticipants(HorseRaceModel model);

    /**
     * Converts a page of {@link HorseRaceModel} instances to a page of {@link HorseRaceResponseDTO}.
     *
     * <p>This method is designed to facilitate the transformation of data models used in business logic
     * into response DTOs suitable for API responses.</p>
     *
     * @param models a {@link Page} of {@link HorseRaceModel} objects to be converted
     * @return a {@link Page} of {@link HorseRaceResponseDTO} containing the corresponding data for the provided models.
     */
    Page<HorseRaceResponseDTO> toResponseDTO(Page<HorseRaceModel> models);
}
