package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindHorseByUuidUseCase {

    private static final Logger logger = Logger.getLogger(FindHorseByUuidUseCase.class);
    private final HorseRepository horseRepository;

    public HorseModel execute(UUID uuid) {
        logger.info("Starting findHorse process for uuid: [{}]", uuid);

        HorseModel horse = horseRepository.findMaybeByUuid(uuid)
                .orElseThrow(() -> new HorseNotFoundException(uuid));

        logger.info("Finished findHorse process for uuid: [{}]", uuid);
        return horse;
    }
}
