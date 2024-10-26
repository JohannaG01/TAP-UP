package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.bets.application.services.BetAsyncService;
import com.johannag.tapup.bets.application.services.BetService;
import com.johannag.tapup.bets.domain.models.BetPayouts;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.dtos.SubmitHorseRaceResultsDTO;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.notifications.application.dtos.SendNotificationsInternalProcessDTO;
import com.johannag.tapup.notifications.application.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.johannag.tapup.bets.application.constants.BetNotificationConstant.FAILED_PAYMENTS_MSG;
import static com.johannag.tapup.bets.application.constants.BetNotificationConstant.SUCCESSFUL_PAYMENTS_MSG;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class SubmitHorseRaceResultsUseCase {

    private static final Logger logger = Logger.getLogger(SubmitHorseRaceResultsUseCase.class);
    private final HorseRaceApplicationMapper horseRaceApplicationMapper;
    private final HorseRaceRepository horseRaceRepository;
    private final FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;
    private final BetAsyncService betAsyncService;
    private final BetService betService;
    private final NotificationService notificationService;

    public HorseRaceModel execute(SubmitHorseRaceResultsDTO dto)
            throws HorseRaceNotFoundException, InvalidHorseRaceStateException, HorseNotAvailableException {

        logger.info("Starting SubmitHorseRaceResults process for horse race {}", dto.getHorseRaceUuid());

        HorseRaceModel horseRace = findOneHorseRaceByUuidUseCase.execute(dto.getHorseRaceUuid());
        validateParticipantsExistsInRaceOrThrow(horseRace, dto.getParticipants());
        validateHorseRaceIsInValidStateToSubmitResultsOrThrow(horseRace);
        var submitHorseRaceResultsForEntityDTO = horseRaceApplicationMapper.toSubmitResultsForEntityDTO(dto);
        HorseRaceModel updatedHorseRace = horseRaceRepository.submitResults(submitHorseRaceResultsForEntityDTO);
        processPaymentsAndHandleAsyncResponse(dto.getHorseRaceUuid());

        logger.info("Finished SubmitHorseRaceResults process for horse race {}", dto.getHorseRaceUuid());
        return updatedHorseRace;
    }

    private void validateHorseRaceIsInValidStateToSubmitResultsOrThrow(HorseRaceModel horseRace)
            throws InvalidHorseRaceStateException {
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

    private void processPaymentsAndHandleAsyncResponse(UUID horseRaceUuid) {
        betAsyncService
                .processPayments(horseRaceUuid)
                .handle((result, throwable) -> sendResultsToAdmins(horseRaceUuid, throwable));
    }

    private BetPayouts sendResultsToAdmins(UUID horseRaceUuid, @Nullable Throwable throwable) {
        BetPayouts betPayouts = betService.generateBetPaymentResults(horseRaceUuid);
        SendNotificationsInternalProcessDTO dto = buildSendNotificationsDTO(horseRaceUuid, betPayouts, throwable);
        notificationService.sendForInternalProcess(dto);
        return betPayouts;
    }

    private SendNotificationsInternalProcessDTO buildSendNotificationsDTO(UUID horseRaceUuid,
                                                                          BetPayouts betPayouts,
                                                                          @Nullable Throwable throwable) {
        String errorMessage = Optional.ofNullable(throwable)
                .map(Throwable::getMessage)
                .orElse("");

        String failureMessage = String.format(FAILED_PAYMENTS_MSG, horseRaceUuid, errorMessage, betPayouts);
        String successfulMessage = String.format(SUCCESSFUL_PAYMENTS_MSG, horseRaceUuid, betPayouts);

        return SendNotificationsInternalProcessDTO.builder()
                .failed(throwable != null)
                .successfulMessage(successfulMessage)
                .failureMessage(failureMessage)
                .build();
    }

}
