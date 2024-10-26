package com.johannag.tapup.horseRaces.application.useCases;


import com.johannag.tapup.bets.application.services.BetAsyncService;
import com.johannag.tapup.bets.application.services.BetService;
import com.johannag.tapup.bets.application.useCases.stubs.BetStubs;
import com.johannag.tapup.bets.domain.models.BetRefunds;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.application.utils.TestUtils;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
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

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CancelHorseRaceUseCaseTests {

    private final HorseRaceModel scheduledHorseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.SCHEDULED);
    private final HorseRaceModel finishedHorseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.FINISHED);
    private final HorseRaceModel cancelledHorseRace = HorseRaceStubs.horseRaceModel(HorseRaceModelState.CANCELLED);
    private final BetRefunds betRefunds = BetStubs.betRefunds();
    @Mock
    private BetAsyncService betAsyncService;
    @Mock
    private BetService betService;
    @Mock
    private HorseRaceRepository horseRaceRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    private FindOneHorseRaceByUuidUseCase findOneHorseRaceByUuidUseCase;
    @Spy
    @InjectMocks
    private CancelHorseRaceUseCase cancelHorseRaceUseCase;

    @Test
    public void horseRaceNotFound() {
        HorseRaceNotFoundException exception = new HorseRaceNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        assertThrows(HorseRaceNotFoundException.class, () ->
                cancelHorseRaceUseCase.execute(UUID.randomUUID()));
    }

    @Test
    public void horseRaceIsInInvalidState() {

        doReturn(finishedHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        assertThrows(InvalidHorseRaceStateException.class, () ->
                cancelHorseRaceUseCase.execute(UUID.randomUUID()));
    }

    @Test
    public void refundBetsFailed() {

        CompletableFuture<Throwable> future = CompletableFuture.completedFuture(null);

        doReturn(scheduledHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        doReturn(cancelledHorseRace)
                .when(horseRaceRepository)
                .cancel(any());

        doReturn(future)
                .when(betAsyncService)
                .processRefunds(any());

        doReturn(betRefunds)
                .when(betService)
                .generateBetsRefundResults(any());

        doNothing()
                .when(notificationService)
                .sendForInternalProcess(any());

        assertEquals(cancelledHorseRace, cancelHorseRaceUseCase.execute(UUID.randomUUID()));
        assertFalse(TestUtils.obtainNotificationsToSendForInnerProcess(notificationService).isFailed());
    }

    @Test
    public void horseRaceCancelledSuccessfully() {
        CompletableFuture<Throwable> future = CompletableFuture.failedFuture(new RuntimeException());

        doReturn(scheduledHorseRace)
                .when(findOneHorseRaceByUuidUseCase)
                .execute(any());

        doReturn(cancelledHorseRace)
                .when(horseRaceRepository)
                .cancel(any());

        doReturn(future)
                .when(betAsyncService)
                .processRefunds(any());

        doReturn(betRefunds)
                .when(betService)
                .generateBetsRefundResults(any());

        doNothing()
                .when(notificationService)
                .sendForInternalProcess(any());

        assertEquals(cancelledHorseRace, cancelHorseRaceUseCase.execute(UUID.randomUUID()));
        assertTrue(TestUtils.obtainNotificationsToSendForInnerProcess(notificationService).isFailed());
    }
}
