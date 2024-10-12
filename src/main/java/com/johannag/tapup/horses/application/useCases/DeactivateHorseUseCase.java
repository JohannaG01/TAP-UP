package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeactivateHorseUseCase {

    private static final Logger logger = Logger.getLogger(DeactivateHorseUseCase.class);
    private final HorseRepository horseRepository;
    private final FindHorseByUuidUseCase findHorseByUuidUseCase;

    public HorseModel execute(UUID uuid) {
        logger.info("Starting DeactivateHorse process for horse UUID %s", uuid.toString());

        findHorseByUuidUseCase.execute(uuid);
        HorseModel horse = horseRepository.deactivateByUuid(uuid);

        logger.info("Finished DeactivateHorse process for horse UUID %s", uuid.toString());
        return horse;
    }
}
