package com.johannag.tapup.horseRaces.domain.mappers;

import com.johannag.tapup.horseRaces.domain.dtos.CreateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;

import java.util.Collection;
import java.util.List;

public interface HorseRaceDomainMapper {

    /**
     * Converts a {@link CreateHorseRaceEntityDTO} and a list of {@link HorseEntity} objects into a
     * {@link HorseRaceEntity}.
     *
     * <p>This method takes a data transfer object (DTO) representing the details of a horse race entity
     * and a list of associated {@link HorseEntity} objects, and converts them into a {@link HorseRaceEntity}
     * that can be persisted in the system.
     *
     * @param dto    the {@link CreateHorseRaceEntityDTO} containing the input data for the horse race entity
     * @param horses the list of {@link HorseEntity} objects representing the horses participating in the race
     * @return a {@link HorseRaceEntity} populated with the data from the provided DTO and horse entities
     */
    HorseRaceEntity toEntity(CreateHorseRaceEntityDTO dto, List<HorseEntity> horses);

    /**
     * Converts a horse race model state object into a horse race entity state object.
     *
     * @param model the horse race model state object to be converted.
     * @return a horse race entity state object corresponding to the provided model.
     */
    HorseRaceEntityState toEntity(HorseRaceModelState model);

    /**
     * Converts a collection of {@link HorseRaceModelState} instances to a list of {@link HorseRaceEntityState} entities.
     *
     * <p>This method is useful for transforming model states used in business logic into
     * entity states that can be persisted in the database.</p>
     *
     * @param model a collection of {@link HorseRaceModelState} objects to be converted
     * @return a list of {@link HorseRaceEntityState} corresponding to the provided model states
     *         or an empty list if the input collection is null or empty
     */
    List<HorseRaceEntityState> toEntity(Collection<HorseRaceModelState> models);

    /**
     * Converts a {@link HorseRaceEntity} to a {@link HorseRaceModel}.
     *
     * @param entity the {@link HorseRaceEntity} to be converted
     * @return a {@link HorseRaceModel} representation of the given entity
     */
    HorseRaceModel toModelWithoutBidirectional(HorseRaceEntity entity);
}
