package com.johannag.tapup.horses.application.mappers;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.UpdateHorseRequestDTO;

import java.util.UUID;

public interface HorseApplicationMapper {

    /**
     * Converts a {@link CreateHorseRequestDTO} to a {@link CreateHorseDTO}.
     * <p>
     * This method maps the input data transfer object (DTO) used for the request to
     * a corresponding DTO that is ready to be processed for horse creation.
     *
     * @param dto the {@link CreateHorseRequestDTO} containing the data from the request.
     * @return a {@link CreateHorseDTO} with the mapped data for horse creation.
     */
    CreateHorseDTO toCreateHorseDTO(CreateHorseRequestDTO dto);

    /**
     * Converts a {@link CreateHorseDTO} to a {@link CreateHorseEntityDTO}.
     *
     * @param dto the DTO containing the details of the horse to be created
     * @return a {@link CreateHorseEntityDTO} populated with the horse details, including a newly generated UUID
     */
    CreateHorseEntityDTO toCreateHorseEntityDTO(CreateHorseDTO dto);

    /**
     * Converts a {@link UpdateHorseRequestDTO} to a {@link UpdateHorseDTO} using the specified horse UUID.
     *
     * @param horseUuid the UUID of the horse to which the patch request applies
     * @param dto the source {@link UpdateHorseRequestDTO} object to be converted
     * @return a {@link UpdateHorseDTO} object representing the converted data, associated with the given horse UUID
     */
    UpdateHorseDTO toUpdateHorseDTO(UUID horseUuid, UpdateHorseRequestDTO dto);

    /**
     * Converts the given {@link UpdateHorseDTO} to an {@link UpdateHorseEntityDTO}.
     *
     * @param dto the data transfer object containing the updated horse information
     * @return an instance of {@link UpdateHorseEntityDTO} populated with the data from the provided {@code dto}
     */
    UpdateHorseEntityDTO toUpdateHorseEntityDTO(UpdateHorseDTO dto);
}
