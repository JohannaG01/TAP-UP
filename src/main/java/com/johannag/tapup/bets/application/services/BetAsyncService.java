package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.domain.models.BetPayouts;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BetAsyncService {

    /**
     * Processes payments for bets associated with a specific horse race.
     *
     * @param horseRaceUuid the unique identifier of the horse race
     * @return a {@link CompletableFuture} containing the payouts for the bets once the processing is complete
     * @throws HorseRaceNotFoundException if no horse race is found for the given UUID
     * @throws InvalidHorseRaceStateException if the horse race is in an invalid state for processing payments
     */
    CompletableFuture<BetPayouts> processPayments(UUID horseRaceUuid) throws HorseRaceNotFoundException, InvalidHorseRaceStateException;
}
