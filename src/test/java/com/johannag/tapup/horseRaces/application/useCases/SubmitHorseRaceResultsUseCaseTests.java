package com.johannag.tapup.horseRaces.application.useCases;

import com.johannag.tapup.bets.application.services.BetAsyncService;
import com.johannag.tapup.bets.application.services.BetService;
import com.johannag.tapup.bets.application.useCases.stubs.BetStubs;
import com.johannag.tapup.bets.domain.models.BetPayouts;
import com.johannag.tapup.horseRaces.application.dtos.SubmitHorseRaceResultsDTO;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.application.utils.TestUtils;
import com.johannag.tapup.horseRaces.domain.dtos.SubmitHorseRaceResultsForEntityDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import com.johannag.tapup.horseRaces.infrastructure.db.adapters.HorseRaceRepository;
import com.johannag.tapup.notifications.application.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SubmitHorseRaceResultsUseCaseTests {

    private final SubmitHorseRaceResultsDTO submitHorseRaceResultsDTO = HorseRaceStubs.submitHorseRaceResultsDTO();
    private final HorseRaceModel scheduledHorseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.SCHEDULED);
    private final HorseRaceModel finishedHorseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.FINISHED);
    private final BetPayouts betPayouts = BetStubs.betPayouts();
    private final SubmitHorseRaceResultsForEntityDTO submitHorseRaceResultsForEntityDTO =
            HorseRaceStubs.submitHorseRaceResultsForEntityDTO();
    @Mock
    private HorseRaceApplicationMapper horseRaceApplicationMapper;
    @Mock
    private HorseRaceRepository horseRaceRepository;
    @Mock
    private FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;
    @Mock
    private BetAsyncService betAsyncService;
    @Mock
    private BetService betService;
    @Mock
    private NotificationService notificationService;
    @Spy
    @InjectMocks
    private SubmitHorseRaceResultsUseCase submitHorseRaceResultsUseCase;

    @Test
    public void horseRaceNotFound() {
        HorseRaceNotFoundException exception = new HorseRaceNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        assertThrows(HorseRaceNotFoundException.class, () ->
                submitHorseRaceResultsUseCase.execute(submitHorseRaceResultsDTO));

    }

    @Test
    public void participantsDoNotExistInRace() {
        scheduledHorseRace.setParticipants(List.of(HorseRaceStubs.participantModel()));

        doReturn(scheduledHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        assertThrows(ParticipantNotFoundException.class, () ->
                submitHorseRaceResultsUseCase.execute(submitHorseRaceResultsDTO));
    }

    @Test
    public void horseRaceInInInvalidState() {

        setParticipantToHoseRace(finishedHorseRace);

        doReturn(finishedHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        assertThrows(InvalidHorseRaceStateException.class, () ->
                submitHorseRaceResultsUseCase.execute(submitHorseRaceResultsDTO));
    }

    @Test
    public void betPaymentsFailed() {

        CompletableFuture<Throwable> future = CompletableFuture.failedFuture(new RuntimeException());

        setParticipantToHoseRace(scheduledHorseRace);

        doReturn(scheduledHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        doReturn(submitHorseRaceResultsForEntityDTO)
                .when(horseRaceApplicationMapper)
                .toSubmitResultsForEntityDTO(any());

        doReturn(finishedHorseRace)
                .when(horseRaceRepository)
                .submitResults(any());

        doReturn(future)
                .when(betAsyncService)
                .processPayments(any());

        doReturn(betPayouts)
                .when(betService)
                .generateBetPaymentResults(any());

        doNothing()
                .when(notificationService)
                .sendForInternalProcess(any());

        assertEquals(finishedHorseRace, submitHorseRaceResultsUseCase.execute(submitHorseRaceResultsDTO));
        assertTrue(TestUtils.obtainNotificationsToSendForInnerProcess(notificationService).isFailed());
    }

    @Test
    public void horseRaceResultsSubmittedSuccessfully() {

        CompletableFuture future = CompletableFuture.completedFuture(null);

        setParticipantToHoseRace(scheduledHorseRace);

        doReturn(scheduledHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        doReturn(submitHorseRaceResultsForEntityDTO)
                .when(horseRaceApplicationMapper)
                .toSubmitResultsForEntityDTO(any());

        doReturn(finishedHorseRace)
                .when(horseRaceRepository)
                .submitResults(any());

        doReturn(future)
                .when(betAsyncService)
                .processPayments(any());

        doReturn(betPayouts)
                .when(betService)
                .generateBetPaymentResults(any());

        doNothing()
                .when(notificationService)
                .sendForInternalProcess(any());

        assertEquals(finishedHorseRace, submitHorseRaceResultsUseCase.execute(submitHorseRaceResultsDTO));
        assertFalse(TestUtils.obtainNotificationsToSendForInnerProcess(notificationService).isFailed());
    }

    private void setParticipantToHoseRace(HorseRaceModel horseRace) {
        ParticipantModel participant = HorseRaceStubs.participantModel();
        UUID firstParticipantUuid = submitHorseRaceResultsDTO.getParticipants().stream()
                .findFirst()
                .map(SubmitHorseRaceResultsDTO.Participant::getUuid)
                .orElse(null);

        participant.setUuid(firstParticipantUuid);
        horseRace.setParticipants(List.of(participant));
    }


}
