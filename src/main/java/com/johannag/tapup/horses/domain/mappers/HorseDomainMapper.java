package com.johannag.tapup.horses.domain.mappers;

import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.domain.models.HorseModelState;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;

import java.util.Collection;
import java.util.List;

public interface HorseDomainMapper {

    /**
     * Converts the provided {@link CreateHorseEntityDTO} into a {@link HorseEntity}.
     *
     * @param dto the DTO containing the details of the horse to be created
     * @return the corresponding {@link HorseEntity} instance
     */
    HorseEntity toEntity(CreateHorseEntityDTO dto);

    /**
     * Converts a {@link HorseEntity} to a {@link HorseModel}.
     *
     * <p>This method takes a {@code HorseEntity} object as input and transforms it
     * into a corresponding {@code HorseModel}. The conversion includes mapping
     * all relevant fields from the entity to the model.</p>
     *
     * @param entity the {@code HorseEntity} to be converted
     * @return the converted {@code HorseModel} object
     */
    HorseModel toModel(HorseEntity entity);

    /**
     * Converts a {@link HorseModelState} to a {@link HorseEntityState}.
     *
     * @param state The horse model state to be converted.
     * @return The corresponding horse entity state for the given model state
     */
    HorseEntityState toEntity(HorseModelState state);

    /**
     * Converts a collection of {@link HorseModelState} to a list of {@link HorseEntityState}.
     *
     * @param states The collection of horse model states to be converted.
     * @return A list of corresponding horse entity states for the given model states
     */
    List<HorseEntityState> toEntity(Collection<HorseModelState> states);
}
