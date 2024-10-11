package com.johannag.tapup.horses.domain.mappers;

import com.johannag.tapup.horses.domain.dtos.CreateHorseEntityDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;

public interface HorseDomainMapper {

    /**
     * Converts the provided {@link CreateHorseEntityDTO} into a {@link HorseEntity}.
     *
     * @param dto the DTO containing the details of the horse to be created
     * @return the corresponding {@link HorseEntity} instance
     */
    HorseEntity toHorseEntity(CreateHorseEntityDTO dto);

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
}
