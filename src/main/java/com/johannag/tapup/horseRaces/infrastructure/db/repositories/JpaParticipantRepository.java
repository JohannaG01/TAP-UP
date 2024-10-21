package com.johannag.tapup.horseRaces.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    /**
     * Retrieves a list of participant UUIDs grouped by horse race UUID.
     *
     * <p>This query fetches the horse race UUID and associated participants.</p>
     *
     * @param horseRaceUuids the list of UUIDs of the horse races for which participants are to be retrieved.
     *                       Must not be null or empty.
     * @return a list of object arrays where each array contains the horse race UUID and the corresponding participants.
     */
    @Query("SELECT p.horseRace.uuid, p FROM ParticipantEntity p " +
            "INNER JOIN FETCH p.horse " +
            "INNER JOIN FETCH p.horseRace " +
            "WHERE p.horseRace.uuid IN :horseRaceUuids"
    )
    List<Object[]> findParticipantsGroupedByHorseRace(@Param("horseRaceUuids") List<UUID> horseRaceUuids);
}