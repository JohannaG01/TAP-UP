package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BetAsyncService {

    /**
     * Processes the payments for a given horse race asynchronously.
     * <p>
     * This method initiates the payment process for the specified horse race identified by its UUID.
     * It will execute the necessary logic to process bets associated with the horse race.
     *
     * @param horseRaceUuid the UUID of the horse race for which payments are to be processed
     * @return a CompletableFuture that will be completed when the payment processing is done
     * @throws HorseRaceNotFoundException     if no horse race is found with the specified UUID
     * @throws InvalidHorseRaceStateException if the horse race is in an invalid state for processing payments
     */
    CompletableFuture<Void> processPayments(UUID horseRaceUuid) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException;
}
