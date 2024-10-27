package com.johannag.tapup.horseRaces.application.mappers;

import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.dtos.SubmitHorseRaceResultsDTO;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.FindHorseRaceEntitiesDTO;
import com.johannag.tapup.horseRaces.domain.dtos.SubmitHorseRaceResultsForEntityDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.queries.FindHorseRacesQuery;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.CreateHorseRaceRequestDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.SubmitHorseRaceResultsRequestDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.UpdateHorseRaceRequestDTO;

import java.util.UUID;

public interface HorseRaceApplicationMapper {

    /**
     * Converts a {@link CreateHorseRaceRequestDTO} to a {@link CreateHorseRaceDTO}.
     *
     * @param dto the {@link CreateHorseRaceRequestDTO} to be converted
     * @return a {@link CreateHorseRaceDTO} that represents the converted data
     */
    CreateHorseRaceDTO toCreateDTO(CreateHorseRaceRequestDTO dto);

    /**
     * Converts a {@link CreateHorseRaceDTO} into a {@link CreateHorseRaceEntityDTO}.
     *
     * <p>This method takes a data transfer object (DTO) representing the details of a horse race and
     * transforms it into an entity DTO suitable for use in creating a new horse race entity in the system.
     *
     * @param dto the {@link CreateHorseRaceDTO} containing the input data for the horse race
     * @return a {@link CreateHorseRaceEntityDTO} populated with the data from the provided {@link CreateHorseRaceDTO}
     */
    CreateHorseRaceEntityDTO toCreateEntityDTO(CreateHorseRaceDTO dto);

    /**
     * Converts the specified {@link UpdateHorseRaceRequestDTO} to an {@link UpdateHorseRaceDTO}
     * for updating a horse race, using the provided horse race UUID.
     *
     * @param horseRaceUuid the UUID of the horse race to update
     * @param dto           the data transfer object containing the update information for the horse race
     * @return an {@link UpdateHorseRaceDTO} object populated with the provided data and horse race UUID
     */
    UpdateHorseRaceDTO toUpdateDTO(UUID horseRaceUuid, UpdateHorseRaceRequestDTO dto);

    /**
     * Converts the provided {@link UpdateHorseRaceDTO} to an {@link UpdateHorseRaceEntityDTO}.
     * <p>
     * This method maps the properties of the given DTO to the corresponding fields in the
     * {@link UpdateHorseRaceEntityDTO}.
     *
     * @param dto the DTO containing the update information for the horse race
     * @return an instance of {@link UpdateHorseRaceEntityDTO} populated with values from the provided DTO
     */
    UpdateHorseRaceEntityDTO toUpdateEntityDTO(UpdateHorseRaceDTO dto);

    /**
     * Converts a {@link FindHorseRacesQuery} to a {@link FindHorseRacesDTO}.
     * This method maps the fields from the query object, which is typically used for
     * filtering and searching horse races, into a DTO that is suitable for further
     * processing or response purposes.
     *
     * @param dto the {@link FindHorseRacesQuery} used for specifying filter criteria.
     * @return a {@link FindHorseRacesDTO} representing the search filters,
     * ready for further processing.
     */
    FindHorseRacesDTO toFindDTO(FindHorseRacesQuery dto);

    /**
     * Converts a {@link FindHorseRacesDTO} to a {@link FindHorseRaceEntitiesDTO}.
     * This method maps the fields from the DTO used for filtering and searching horse races
     * into an entity DTO suitable for data access or persistence operations.
     *
     * @param dto the {@link FindHorseRacesDTO} containing the filter criteria.
     * @return a {@link FindHorseRaceEntitiesDTO} that represents the same data as the
     * provided DTO, formatted for use in entity operations.
     */
    FindHorseRaceEntitiesDTO toFindEntitiesDTO(FindHorseRacesDTO dto);

    /**
     * Converts the provided {@link SubmitHorseRaceResultsRequestDTO} into a
     * {@link SubmitHorseRaceResultsDTO} object for the specified horse race.
     *
     * @param horseRaceUuid The unique identifier of the horse race for which
     *                      the results are being submitted.
     * @param dto           The DTO containing the results to be submitted, which includes
     *                      the end time of the race and the participant results.
     * @return A {@link SubmitHorseRaceResultsDTO} object populated with the data
     * from the provided DTO, associated with the specified horse race.
     */
    SubmitHorseRaceResultsDTO toSubmitResultsDTO(UUID horseRaceUuid, SubmitHorseRaceResultsRequestDTO dto);

    /**
     * Converts a {@link SubmitHorseRaceResultsDTO} into a
     * {@link SubmitHorseRaceResultsForEntityDTO}.
     *
     * @param dto the data transfer object containing the results of the horse race
     * @return a {@link SubmitHorseRaceResultsForEntityDTO} that represents the same
     * information as the provided {@link SubmitHorseRaceResultsDTO}
     */
    SubmitHorseRaceResultsForEntityDTO toSubmitResultsForEntityDTO(SubmitHorseRaceResultsDTO dto);
}
