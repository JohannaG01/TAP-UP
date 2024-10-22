package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.application.useCases.processBetsPayment.ProcessBetsPaymentUseCase;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class BetAsyncServiceImpl implements BetAsyncService {

    private final ProcessBetsPaymentUseCase processBetsPaymentUseCase;

    @Override
    @Async
    public CompletableFuture<Void> processPayments(UUID horseRaceUuid) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException {
        processBetsPaymentUseCase.execute(horseRaceUuid);
        return CompletableFuture.completedFuture(null);
    }
}
