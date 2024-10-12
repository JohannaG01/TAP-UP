package com.johannag.tapup.horses.presentation.mappers;

import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import org.springframework.data.domain.Page;

public interface HorsePresentationMapper {

    /**
     * Converts a {@link HorseModel} instance to a {@link HorseResponseDTO}.
     *
     * @param horse the {@link HorseModel} to be mapped
     * @return a {@link HorseResponseDTO} representation of the provided {@link HorseModel}
     */
    HorseResponseDTO toResponseDTO(HorseModel horse);

    /**
     * Converts a {@link Page} of {@link HorseModel} objects to a {@link Page} of
     * {@link HorseResponseDTO} objects.
     *
     * @param horses The page of horse models to convert, containing the horse data to be
     *               transformed into response DTOs.
     * @return A {@link Page} of {@link HorseResponseDTO} containing the converted horse
     *         data. The returned page will have the same pagination information as the input
     *         page.
     */
    Page<HorseResponseDTO> toResponseDTO(Page<HorseModel> horses);
}
