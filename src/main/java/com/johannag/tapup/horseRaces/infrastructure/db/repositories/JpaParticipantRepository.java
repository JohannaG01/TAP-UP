package com.johannag.tapup.horseRaces.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaParticipantRepository extends JpaRepository<ParticipantEntity, Long> {

    /**
     * Retrieves a {@link ParticipantEntity} by its unique identifier (UUID).
     *
     * <p>This method is used to fetch a participant entity from the database based on the provided UUID.</p>
     *
     * @param uuid the unique identifier of the participant to be retrieved
     * @return the {@link ParticipantEntity} associated with the given UUID, or null if not found
     */
    ParticipantEntity findOneByUuid(UUID uuid);
}