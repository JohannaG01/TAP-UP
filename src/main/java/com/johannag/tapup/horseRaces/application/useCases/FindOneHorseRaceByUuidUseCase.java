package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindOneHorseRaceByUuidUseCase {

    private static final Logger logger = Logger.getLogger(FindOneHorseRaceByUuidUseCase.class);
    private final HorseRaceRepository horseRaceRepository;

    public HorseRaceModel execute(UUID uuid) throws HorseRaceNotFoundException {
        logger.info("Starting FindHorseRace process for race UUID {}", uuid.toString());

        HorseRaceModel horseRace = horseRaceRepository.findOneMaybeByUuid(uuid)
                .orElseThrow(() -> new HorseRaceNotFoundException(uuid));

        logger.info("Finished FindHorseRace process for race UUID {}", uuid.toString());
        return horseRace;
    }
}
