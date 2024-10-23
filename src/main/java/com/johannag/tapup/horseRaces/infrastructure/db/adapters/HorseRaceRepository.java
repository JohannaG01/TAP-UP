package com.johannag.tapup.horseRaces.infrastructure.db.adapters;

import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.dtos.FindHorseRaceEntitiesDTO;
import com.johannag.tapup.horseRaces.domain.dtos.SubmitHorseRaceResultsForEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface HorseRaceRepository {

    /**
     * Creates a new horse race model from the given {@link CreateHorseRaceEntityDTO}.
     *
     * <p>This method takes a data transfer object (DTO) that contains the details of a horse race entity
     * and creates a corresponding {@link HorseRaceModel} in the system. The created model represents
     * the newly added horse race and contains all relevant information from the provided entity DTO.
     *
     * @param dto the {@link CreateHorseRaceEntityDTO} containing the input data for the horse race entity
     * @return a {@link HorseRaceModel} representing the newly created horse race
     */
    HorseRaceModel create(CreateHorseRaceEntityDTO dto);

    /**
     * Retrieves an optional horse race model by its UUID.
     *
     * @param uuid the UUID of the horse race to retrieve
     * @return an {@link Optional} containing the {@link HorseRaceModel} associated with the given UUID,
     * or an empty {@link Optional} if no such horse race exists
     */
    Optional<HorseRaceModel> findOneMaybeByUuid(UUID uuid);

    /**
     * Updates the horse race using the provided {@link UpdateHorseRaceEntityDTO}.
     * <p>
     * This method takes an {@link UpdateHorseRaceEntityDTO} as input, which contains the new
     * values for the horse race. It retrieves the corresponding horse race entity, updates its
     * properties based on the values in the DTO, and then persists the changes to the database.
     *
     * @param dto the DTO containing the updated information for the horse race
     * @return the updated {@link HorseRaceModel} representing the state of the horse race after the update
     */
    HorseRaceModel update(UpdateHorseRaceEntityDTO dto);

    /**
     * Finds and returns a paginated list of {@link HorseRaceModel} entities based on the provided
     * search criteria in the {@link FindHorseRaceEntitiesDTO}.
     * This method applies filters such as horse UUID, race state, and time ranges to retrieve
     * horse races matching the specified conditions, with support for pagination.
     *
     * @param dto the {@link FindHorseRaceEntitiesDTO} containing filter criteria.
     * @return a {@link Page} of {@link HorseRaceModel} that matches the search criteria.
     * The page will contain the filtered horse races and pagination information.
     */
    Page<HorseRaceModel> findAll(FindHorseRaceEntitiesDTO dto);

    /**
     * Finds a horse race model by the given participant UUID.
     *
     * <p>This method retrieves an optional {@link HorseRaceModel} instance associated with the specified
     * participant UUID. If a horse race with the specified participant is found, the method returns an
     * {@link Optional} containing the horse race model; otherwise, it returns an empty {@link Optional}.</p>
     *
     * @param participantUuid the UUID of the participant whose horse race is to be found.
     * @return an {@link Optional} containing the {@link HorseRaceModel} if found, or an empty {@link Optional} if not.
     */
    Optional<HorseRaceModel> findOneMaybeByParticipantUuid(UUID participantUuid);

    /**
     * Submits the results for a horse race based on the provided data transfer object (DTO).
     *
     * @param dto the data transfer object containing the results to submit for the horse race
     * @return the updated {@link HorseRaceModel} after submitting the results
     */
    HorseRaceModel submitResults(SubmitHorseRaceResultsForEntityDTO dto);

    /**
     * Cancels a horse race identified by the given UUID.
     *
     * <p>This method changes the state of the horse race to canceled.
     * It will throw an exception if the horse race is not found
     * or if the current state of the race does not allow for cancellation.</p>
     *
     * @param horseRaceUuid the unique identifier of the horse race to be canceled
     * @return the updated {@link HorseRaceModel} after cancellation
     */
    HorseRaceModel cancel(UUID horseRaceUuid);
}
