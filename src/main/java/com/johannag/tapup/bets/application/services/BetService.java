package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.application.exceptions.InsufficientBalanceException;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;

public interface BetService {

    /**
     * Creates a new {@link BetModel} instance from the provided {@link CreateBetDTO}.
     *
     * @param dto the {@link CreateBetDTO} containing the details for the new bet
     * @return a {@link BetModel} representing the newly created bet
     */
    BetModel create(CreateBetDTO dto) throws UserNotFoundException, ParticipantNotFoundException,
            InvalidHorseRaceStateException, InsufficientBalanceException;

    /**
     * Finds all bets matching the criteria specified in the provided {@link FindBetsDTO}.
     *
     * <p>This method searches for bets based on the search criteria specified in the {@link FindBetsDTO},
     * including filters like bet states, amount ranges, horse race UUID, start time ranges, and more.
     *
     * <p>The results are returned in a paginated {@link Page} of {@link BetModel} objects. If the user specified
     * in the {@link FindBetsDTO} is not found, a {@link UserNotFoundException} is thrown.
     *
     * @param dto the {@link FindBetsDTO} object containing the search criteria
     * @return a paginated {@link Page} of {@link BetModel} objects matching the search criteria
     * @throws UserNotFoundException if the user specified in the DTO does not exist
     */
    Page<BetModel> findUserAll(FindBetsDTO dto) throws UserNotFoundException;
}
