package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.application.dtos.SubmitHorseRaceResultsDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class SubmitHorseRaceResultsUseCase {

    private static final Logger logger = Logger.getLogger(SubmitHorseRaceResultsUseCase.class);
    private final HorseRaceApplicationMapper horseRaceApplicationMapper;
    private final HorseRaceRepository horseRaceRepository;
    private final FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;
    private final PropertyPlaceholderAutoConfiguration propertyPlaceholderAutoConfiguration;

    public HorseRaceModel execute(SubmitHorseRaceResultsDTO dto)
            throws HorseRaceNotFoundException, InvalidHorseRaceStateException, HorseNotAvailableException {

        logger.info("Starting SubmitHorseRaceResults process for horse race {}", dto.getHorseRaceUuid());

        HorseRaceModel horseRace = findOneHorseRaceByUuidUseCase.execute(dto.getHorseRaceUuid());
        validateParticipantsExistsInRaceOrThrow(horseRace, dto.getParticipants());
        validateHorseRaceIsInValidStateToSubmitResultsOrThrow(horseRace);
        var submitHorseRaceResultsForEntityDTO = horseRaceApplicationMapper.toSubmitResultsForEntityDTO(dto);
        HorseRaceModel updatedHorseRace = horseRaceRepository.submitResults(submitHorseRaceResultsForEntityDTO);
        //TODO Call use case in bet to process payment

        logger.info("Finished SubmitHorseRaceResults process for horse race {}", dto.getHorseRaceUuid());
        return updatedHorseRace;
    }

    private void validateHorseRaceIsInValidStateToSubmitResultsOrThrow(HorseRaceModel horseRace) throws InvalidHorseRaceStateException {
        if (!horseRace.isScheduled() || !horseRace.hasAlreadyStarted()) {
            throw new InvalidHorseRaceStateException(String.format("Horse race UUID %s must be SCHEDULED and had " +
                    "already started in order to submit results", horseRace.getUuid())
            );
        }
    }

    private void validateParticipantsExistsInRaceOrThrow(HorseRaceModel horseRace,
                                                         List<SubmitHorseRaceResultsDTO.Participant> participantsDTO)
            throws ParticipantNotFoundException {

        List<UUID> participantsUuids = participantsDTO.stream()
                .map(SubmitHorseRaceResultsDTO.Participant::getUuid)
                .toList();

        List<UUID> missingParticipants = horseRace.checkForMissingParticipants(participantsUuids);

        if (!missingParticipants.isEmpty()) {
            throw new ParticipantNotFoundException(missingParticipants, horseRace.getUuid());
        }
    }
}
