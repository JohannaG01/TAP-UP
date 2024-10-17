package com.johannag.tapup.horses.application.mappers;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.dtos.FindHorseEntitiesDTO;
import com.johannag.tapup.horses.domain.dtos.UpdateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.presentation.dtos.queries.FindHorsesQuery;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.UpdateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseStateDTO;

import java.util.Collection;
import java.util.List;
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
    CreateHorseDTO toCreateDTO(CreateHorseRequestDTO dto);

    /**
     * Converts a {@link CreateHorseDTO} to a {@link CreateHorseEntityDTO}.
     *
     * @param dto the DTO containing the details of the horse to be created
     * @return a {@link CreateHorseEntityDTO} populated with the horse details, including a newly generated UUID
     */
    CreateHorseEntityDTO toCreateEntityDTO(CreateHorseDTO dto);

    /**
     * Converts a {@link UpdateHorseRequestDTO} to a {@link UpdateHorseDTO} using the specified horse UUID.
     *
     * @param horseUuid the UUID of the horse to which the patch request applies
     * @param dto       the source {@link UpdateHorseRequestDTO} object to be converted
     * @return a {@link UpdateHorseDTO} object representing the converted data, associated with the given horse UUID
     */
    UpdateHorseDTO toUpdateDTO(UUID horseUuid, UpdateHorseRequestDTO dto);

    /**
     * Converts the given {@link UpdateHorseDTO} to an {@link UpdateHorseEntityDTO}.
     *
     * @param dto the data transfer object containing the updated horse information
     * @return an instance of {@link UpdateHorseEntityDTO} populated with the data from the provided {@code dto}
     */
    UpdateHorseEntityDTO toUpdateEntityDTO(UpdateHorseDTO dto);

    /**
     * Converts a {@link FindHorsesQuery} object to a {@link FindHorsesDTO} object.
     *
     * @param dto the {@link FindHorsesQuery} object containing the search criteria
     * @return a {@link FindHorsesDTO} object with the corresponding values from the input query
     */
    FindHorsesDTO toFindDTO(FindHorsesQuery dto);

    /**
     * Converts a {@link FindHorsesDTO} to a {@link FindHorseEntitiesDTO.Builder}.
     *
     * @param dto the Data Transfer Object containing search criteria for horses
     * @return a {@link FindHorseEntitiesDTO.Builder} that represents the entity filters based on the provided DTO
     */
    FindHorseEntitiesDTO.Builder toFindEntitiesDTO(FindHorsesDTO dto);

    /**
     * Converts a {@link HorseStateDTO} to a {@link HorseModelState}.
     *
     * @param state The data transfer object containing the state information of a horse.
     * @return The corresponding {@link HorseModelState} representation of the given DTO.
     */
    HorseModelState toModel(HorseStateDTO state);

    /**
     * Converts a collection of {@link HorseStateDTO} objects to a list of {@link HorseModelState} objects.
     *
     * @param states The collection of data transfer objects containing state information of horses.
     * @return A list of corresponding {@link HorseModelState} representations for the given DTOs.
     */
    List<HorseModelState> toModel(Collection<HorseStateDTO> states);
}
