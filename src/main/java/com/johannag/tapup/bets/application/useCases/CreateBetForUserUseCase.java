package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.exceptions.InsufficientBalanceException;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.users.application.dtos.SubtractUserFundsDTO;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.domain.models.UserModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CreateBetForUserUseCase {

    private static final Logger logger = Logger.getLogger(CreateBetForUserUseCase.class);
    private final BetApplicationMapper betApplicationMapper;
    private final UserService userService;
    private final HorseRaceService horseRaceService;
    private final BetRepository betRepository;

    @Transactional
    public BetModel execute(CreateBetDTO dto) throws UserNotFoundException, ParticipantNotFoundException,
            InvalidHorseRaceStateException, InsufficientBalanceException {
        logger.info("Starting createBet process for User Uuid {}", dto.getUserUuid());

        UserModel user = userService.findOneByUuid(dto.getUserUuid());
        validateUserHasEnoughBalanceOrThrow(user, dto.getAmount());
        HorseRaceModel horseRace = horseRaceService.findByParticipantUuid(dto.getParticipantUuid());
        validateHorseRaceStateIsValidOrThrow(horseRace);
        CreateBetEntityDTO createBetEntityDTO = betApplicationMapper.toCreateEntityDTO(dto);
        BetModel bet = betRepository.create(createBetEntityDTO);
        SubtractUserFundsDTO subtractUserFundsDTO = new SubtractUserFundsDTO(dto.getUserUuid(), dto.getAmount());
        userService.subtractFunds(subtractUserFundsDTO);

        logger.info("Finishing createBet process for User Uuid {}", dto.getUserUuid());
        return bet;
    }

    private void validateUserHasEnoughBalanceOrThrow(UserModel user, BigDecimal amount) {
        logger.info("Validating user's balance for User Uuid {}", user.getUuid());
        if (!user.hasEnoughBalance(amount)) {
            throw new InsufficientBalanceException(user.getUuid(), amount);
        }
    }

    private void validateHorseRaceStateIsValidOrThrow(HorseRaceModel horseRace) {
        logger.info("Validating horse race state for Uuid {}", horseRace.getUuid());
        if (!horseRace.isScheduled()) {
            throw new InvalidHorseRaceStateException("The participant must be in a scheduled horse race to place a " +
                    "bet.");
        }
    }

}
