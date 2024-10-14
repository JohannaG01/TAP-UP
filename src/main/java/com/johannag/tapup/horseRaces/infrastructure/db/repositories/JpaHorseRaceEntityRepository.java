package com.johannag.tapup.horseRaces.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHorseRaceEntityRepository extends JpaRepository<HorseRaceEntity, Long> {
}