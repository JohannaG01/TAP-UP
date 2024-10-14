package com.johannag.tapup.horseRaces.presentation.mappers;

import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.ParticipantResponseDTO;

public interface ParticipantPresentationMapper {

    /**
     * Converts a {@link ParticipantModel} to a {@link ParticipantResponseDTO}.
     * <p>
     * This method transforms the data from the domain model {@link ParticipantModel}
     * to a response DTO {@link ParticipantResponseDTO}, which is used to expose
     * the participant details to the client in a suitable format.
     *
     * @param model the {@link ParticipantModel} object containing the participant's domain data
     * @return a {@link ParticipantResponseDTO} containing the participant data for the client
     */
    ParticipantResponseDTO toResponseDTO(ParticipantModel model);
}
