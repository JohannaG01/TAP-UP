package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;

import java.util.UUID;

public interface BetAsyncService {

    /**
     * Processes payments for the specified horse race.
     * <p>
     * This method handles the payment processing logic for the given horse race identified
     * by its UUID. It may validate the state of the horse race and execute necessary
     * payment transactions based on the race results.
     * </p>
     *
     * @param horseRaceUuid the UUID of the horse race to process payments for
     * @throws HorseRaceNotFoundException if the horse race with the specified UUID cannot be found
     * @throws InvalidHorseRaceStateException if the horse race is not in a valid state to process payments
     */
    void processPayments(UUID horseRaceUuid) throws HorseRaceNotFoundException, InvalidHorseRaceStateException;
}
