package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.configs.HorseConfig;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import com.johannag.tapup.horses.infrastructure.db.dtos.FindByUuidStatesAndDatesDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ValidateHorsesAvailabilityUseCase {

    private static final Logger logger = Logger.getLogger(ValidateHorsesAvailabilityUseCase.class);
    private final HorseRepository horseRepository;
    private final HorseConfig horseConfig;

    public void execute(List<UUID> uuid, LocalDateTime raceStartTime, List<UUID> horseRaceUuidsToExclude)
            throws HorseNotFoundException, HorseNotAvailableException {
        logger.info("Starting VerifyHorsesAvailability process for uuid {}", uuid);

        validateExistHorsesOrThrow(uuid);
        validateHorseAreAvailable(uuid, raceStartTime, horseRaceUuidsToExclude);

        logger.info("Finished VerifyHorsesAvailability process for uuid {}", uuid);
    }

    private void validateExistHorsesOrThrow(List<UUID> uuids) throws HorseNotFoundException {
        List<HorseModel> existentHorses = horseRepository.findActiveByUuidIn(uuids);

        List<UUID> existentHorsesUuids = existentHorses.stream()
                .map(HorseModel::getUuid)
                .toList();

        List<UUID> nonExistentHorsesUuids = uuids.stream()
                .filter(uuid -> !existentHorsesUuids.contains(uuid))
                .toList();

        if (!nonExistentHorsesUuids.isEmpty()) {
            throw new HorseNotFoundException(nonExistentHorsesUuids);
        }
    }

    private void validateHorseAreAvailable(List<UUID> uuids, LocalDateTime raceStartTime,
                                           List<UUID> horseRaceUuidsToExclude)
            throws HorseNotAvailableException {

        long daysToRecover = horseConfig.getRecoveryTimeInDays();

        LocalDateTime fromDateTime = raceStartTime.minusDays(daysToRecover);
        LocalDateTime toDateTime = raceStartTime.plusDays(daysToRecover);

        List<UUID> unAvailableHorsesUuids =
                obtainUnavailableHorsesUuids(uuids, fromDateTime, toDateTime, raceStartTime, horseRaceUuidsToExclude);

        if (!unAvailableHorsesUuids.isEmpty()) {
            throw new HorseNotAvailableException(unAvailableHorsesUuids, daysToRecover, raceStartTime);
        }
    }

    private List<UUID> obtainUnavailableHorsesUuids(List<UUID> uuids, LocalDateTime fromDateTime,
                                                    LocalDateTime toDateTime, LocalDateTime raceStartTime,
                                                    List<UUID> horseRaceUuidsToExclude) {
        FindByUuidStatesAndDatesDTO dto = FindByUuidStatesAndDatesDTO.builder()
                .uuids(uuids)
                .pastDateTime(fromDateTime)
                .futureDateTime(toDateTime)
                .raceDateTime(raceStartTime)
                .horseRaceUuidsToExclude(horseRaceUuidsToExclude)
                .build();

        return horseRepository
                .findByUuidsInScheduledRaceBeforeOrInFinishedRaceAfter(dto)
                .stream()
                .map(HorseModel::getUuid)
                .toList();
    }
}
