package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.application.useCases.ProcessPaymentsUseCase;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BetAsyncServiceImpl implements BetAsyncService {

    private final ProcessPaymentsUseCase processPaymentsUseCase;

    @Override
    @Async
    public void processPayments(UUID horseRaceUuid) throws HorseRaceNotFoundException, InvalidHorseRaceStateException {
        processPaymentsUseCase.execute(horseRaceUuid);
    }
}
