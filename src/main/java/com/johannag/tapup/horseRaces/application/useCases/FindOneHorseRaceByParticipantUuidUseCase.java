package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.horses.domain.models.HorseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindOneHorseRaceByParticipantUuidUseCase {

    private static final Logger logger = Logger.getLogger(FindOneHorseRaceByParticipantUuidUseCase.class);
    private final HorseRaceRepository horseRaceRepository;

    public HorseRaceModel execute(UUID uuid) {
        logger.info("Starting findOneHorceRaceByParticipant process for participant UUID {}", uuid);

        HorseRaceModel horseRace = horseRaceRepository.findOneMaybeByParticipantUuid(uuid)
                .orElseThrow(() -> new ParticipantNotFoundException(uuid));

        logger.info("Finished findOneHorceRaceByParticipant process for participant UUID {}", uuid);
        return horseRace;
    }
}
