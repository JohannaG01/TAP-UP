package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.application.useCases.payments.ProcessPaymentsUseCase;
import com.johannag.tapup.bets.domain.models.BetPayouts;
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

    private final ProcessPaymentsUseCase processPaymentsUseCase;

    @Override
    @Async
    public CompletableFuture<BetPayouts> processPayments(UUID horseRaceUuid) throws HorseRaceNotFoundException, InvalidHorseRaceStateException {
        return CompletableFuture.completedFuture(processPaymentsUseCase.execute(horseRaceUuid));
    }
}
