package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.domain.UpdateHorseRaceEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.services.HorseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateHorseRaceUseCase {

    private static final Logger logger = Logger.getLogger(UpdateHorseRaceUseCase.class);
    private final HorseRaceApplicationMapper horseRaceApplicationMapper;
    private final HorseService horseService;
    private final HorseRaceRepository horseRaceRepository;
    private final FindOneHorseRacesUseCase findOneHorseRacesUseCase;

    public HorseRaceModel execute(UpdateHorseRaceDTO dto)
            throws HorseNotFoundException, InvalidHorseRaceStateException, HorseNotAvailableException {
        logger.info("Starting UpdateHorseRace process for race UUID {}", dto.getHorseRaceUuid());

        UUID horseRaceUuid = dto.getHorseRaceUuid();
        HorseRaceModel horseRace = findOneHorseRacesUseCase.execute(horseRaceUuid);
        validateHorseRaceIsScheduledOrThrow(horseRace);
        horseService.validateHorsesAvailability(horseRace.getHorseUuids(), dto.getStartTime(), List.of(horseRaceUuid));
        UpdateHorseRaceEntityDTO updateHorseRaceEntityDTO = horseRaceApplicationMapper.toUpdateEntityDTO(dto);
        HorseRaceModel updatedHorseRace = horseRaceRepository.update(updateHorseRaceEntityDTO);

        logger.info("Finished UpdateHorseRace process for race UUID {}", dto.getHorseRaceUuid());
        return updatedHorseRace;
    }

    private void validateHorseRaceIsScheduledOrThrow(HorseRaceModel horseRace) {
        if (!horseRace.isScheduled()) {
            throw new InvalidHorseRaceStateException(String.format("Cannot update horse race with UUID %s: it must be" +
                    " in SCHEDULED state.", horseRace.getUuid()));
        }
    }
}
