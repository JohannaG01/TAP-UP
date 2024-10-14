package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindOneHorseRacesUseCase {

    private static final Logger logger = Logger.getLogger(FindOneHorseRacesUseCase.class);
    private final HorseRaceRepository horseRaceRepository;

    public HorseRaceModel execute(UUID uuid) {
        logger.info("Starting FindHorseRace process for race UUID {}", uuid.toString());

        HorseRaceModel horseRace = horseRaceRepository.findOneMaybeByUuid(uuid)
                .orElseThrow(() -> new HorseRaceNotFoundException(uuid));

        logger.info("Finished FindHorseRace process for race UUID {}", uuid.toString());
        return horseRace;
    }
}
