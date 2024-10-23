package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.bets.application.services.BetAsyncService;
import com.johannag.tapup.bets.application.services.BetService;
import com.johannag.tapup.bets.domain.models.BetRefunds;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.notifications.application.dtos.SendNotificationsInternalProcessDTO;
import com.johannag.tapup.notifications.application.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.johannag.tapup.bets.application.constants.BetNotificationConstant.FAILED_REFUNDS_MSG;
import static com.johannag.tapup.bets.application.constants.BetNotificationConstant.SUCCESSFUL_REFUNDS_MSG;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class CancelHorseRaceUseCase {

    private static final Logger logger = Logger.getLogger(CancelHorseRaceUseCase.class);
    private final BetAsyncService betAsyncService;
    private final BetService betService;
    private final HorseRaceRepository horseRaceRepository;
    private final NotificationService notificationService;
    private final FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;

    public HorseRaceModel execute(UUID horseRaceUuid) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException {
        logger.info("Starting cancelHorseRace process for horseRace {}", horseRaceUuid);

        HorseRaceModel horseRace = findOneHorseRaceByUuidUseCase.execute(horseRaceUuid);
        validateHorseRaceStateIsValidOrThrow(horseRace);
        HorseRaceModel cancelledHorseRace = horseRaceRepository.cancel(horseRaceUuid);
        processRefundsAndHandleAsyncResponse(horseRaceUuid);

        logger.info("Finished cancelHorseRace process for horseRace {}", horseRaceUuid);
        return cancelledHorseRace;
    }

    private void validateHorseRaceStateIsValidOrThrow(HorseRaceModel horseRace) throws InvalidHorseRaceStateException {
        if (!horseRace.isScheduled()) {
            throw new InvalidHorseRaceStateException(String.format("Cannot cancel HorseRace with UUID %s: it must be " +
                    "in SCHEDULED state.", horseRace.getUuid()));
        }
    }

    private void processRefundsAndHandleAsyncResponse(UUID horseRaceUuid) {
        betAsyncService
                .processRefunds(horseRaceUuid)
                .handle((result, throwable) -> sendResultsToAdmins(horseRaceUuid, throwable));
    }

    private BetRefunds sendResultsToAdmins(UUID horseRaceUuid, @Nullable Throwable throwable) {
        BetRefunds betRefunds = betService.generateBetsRefundResults(horseRaceUuid);
        SendNotificationsInternalProcessDTO dto = buildSendNotificationsDTO(horseRaceUuid, betRefunds, throwable);
        notificationService.sendForInternalProcess(dto);
        return betRefunds;
    }

    private SendNotificationsInternalProcessDTO buildSendNotificationsDTO(UUID horseRaceUuid,
                                                                          BetRefunds betRefunds,
                                                                          @Nullable Throwable throwable) {
        String errorMessage = Optional.ofNullable(throwable)
                .map(Throwable::getMessage)
                .orElse("");

        String failureMessage = String.format(FAILED_REFUNDS_MSG, horseRaceUuid, errorMessage, betRefunds);
        String successfulMessage = String.format(SUCCESSFUL_REFUNDS_MSG, horseRaceUuid, betRefunds);

        return SendNotificationsInternalProcessDTO.builder()
                .failed(throwable != null)
                .successfulMessage(successfulMessage)
                .failureMessage(failureMessage)
                .build();
    }

}
