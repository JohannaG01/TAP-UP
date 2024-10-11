package com.johannag.tapup.horses.presentation.mappers;

import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;

public interface HorsePresentationMapper {

    /**
     * Converts a {@link HorseModel} instance to a {@link HorseResponseDTO}.
     *
     * @param horse the {@link HorseModel} to be mapped
     * @return a {@link HorseResponseDTO} representation of the provided {@link HorseModel}
     */
    HorseResponseDTO toHorseResponseDTO(HorseModel horse);
}
