package com.johannag.tapup.horseRaces.domain.mappers;

import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;

public interface ParticipantDomainMapper {

    /**
     * Converts a {@link ParticipantEntity} to a {@link ParticipantModel} without
     * including the horse race information.
     *
     * @param entity the {@link ParticipantEntity} to convert
     * @return a {@link ParticipantModel} representation of the given entity without
     * the horse race information
     */
    ParticipantModel toModelWithoutRace(ParticipantEntity entity);
}
