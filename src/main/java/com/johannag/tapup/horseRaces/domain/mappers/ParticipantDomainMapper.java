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

    /**
     * Converts a {@link ParticipantEntity} to a {@link ParticipantModel} without establishing bidirectional relationships.
     *
     * <p>This method is useful for creating a model representation of a participant
     * while avoiding the complexities of bidirectional associations, which can lead to circular references.</p>
     *
     * @param entity the entity containing the details of the participant to be converted
     * @return a {@link ParticipantModel} representing the converted participant model
     */
    ParticipantModel toModel(ParticipantEntity entity);
}
