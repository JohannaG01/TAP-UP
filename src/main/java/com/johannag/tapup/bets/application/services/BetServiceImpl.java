package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.application.exceptions.InsufficientBalanceException;
import com.johannag.tapup.bets.application.useCases.CreateBetForUserUseCase;
import com.johannag.tapup.bets.application.useCases.FindBetsForUserUseCase;
import com.johannag.tapup.bets.application.useCases.GenerateBetDetailsForHorseRaceUseCase;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BetServiceImpl implements BetService {

    private final CreateBetForUserUseCase createBetForUserUseCase;
    private final FindBetsForUserUseCase findBetsForUserUseCase;
    private final GenerateBetDetailsForHorseRaceUseCase generateBetDetailsForHorseRaceUseCase;

    @Override
    public BetModel create(CreateBetDTO dto) throws UserNotFoundException, ParticipantNotFoundException,
            InvalidHorseRaceStateException, InsufficientBalanceException {
        return createBetForUserUseCase.execute(dto);
    }

    @Override
    public Page<BetModel> findUserAll(FindBetsDTO dto) throws UserNotFoundException {
        return findBetsForUserUseCase.execute(dto);
    }

    @Override
    public List<BetSummaryModel> generateBetDetails(UUID horseRaceUuid) throws HorseRaceNotFoundException {
        return generateBetDetailsForHorseRaceUseCase.execute(horseRaceUuid);
    }
}
