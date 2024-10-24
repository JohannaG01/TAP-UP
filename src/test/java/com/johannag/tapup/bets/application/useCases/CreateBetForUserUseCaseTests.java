package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.exceptions.InsufficientBalanceException;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.application.useCases.stubs.BetStubs;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.application.useCases.stubs.UserStubs;
import com.johannag.tapup.users.domain.models.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateBetForUserUseCaseTests {

    private final CreateBetDTO highCreateBetDTO = BetStubs.createBetDTO(new BigDecimal(8000));
    private final CreateBetDTO lowCreateBetDTO = BetStubs.createBetDTO(new BigDecimal(1));
    private final HorseRaceModel scheduledHorseRace = HorseRaceStubs.horseRaceStub(HorseRaceModelState.SCHEDULED);
    private final HorseRaceModel finishedHorseRace = HorseRaceStubs.horseRaceStub(HorseRaceModelState.FINISHED);
    private final UserModel user = UserStubs.userModel();
    private final CreateBetEntityDTO createBetEntityDTO = BetStubs.createBetEntityDTO();
    private final BetModel bet = BetStubs.betModel();
    @Mock
    private BetApplicationMapper betApplicationMapper;
    @Mock
    private UserService userService;
    @Mock
    private HorseRaceService horseRaceService;
    @Mock
    private BetRepository betRepository;
    @Spy
    @InjectMocks
    private CreateBetForUserUseCase createBetForUserUseCase;

    @Test
    public void userNotFound() {
        UserNotFoundException exception = new UserNotFoundException(lowCreateBetDTO.getUserUuid());

        doThrow(exception)
                .when(userService)
                .findOneByUuid(any());

        assertThrows(UserNotFoundException.class, () -> createBetForUserUseCase.execute(lowCreateBetDTO));
    }

    @Test
    public void userHasNotEnoughBalance() {
        user.setBalance(BigDecimal.ZERO);

        doReturn(user)
                .when(userService)
                .findOneByUuid(any());

        assertThrows(InsufficientBalanceException.class, () -> createBetForUserUseCase.execute(highCreateBetDTO));

    }

    @Test
    public void participantNotFound() {
        user.setBalance(new BigDecimal(10000));
        ParticipantNotFoundException exception = new ParticipantNotFoundException(lowCreateBetDTO.getUserUuid());

        doReturn(user)
                .when(userService)
                .findOneByUuid(any());

        doThrow(exception)
                .when(horseRaceService)
                .findByParticipantUuid(any());

        assertThrows(ParticipantNotFoundException.class, () -> createBetForUserUseCase.execute(lowCreateBetDTO));

    }

    @Test
    public void horseRaceInvalidState() {
        user.setBalance(new BigDecimal(10000));

        doReturn(user)
                .when(userService)
                .findOneByUuid(any());

        doReturn(finishedHorseRace)
                .when(horseRaceService)
                .findByParticipantUuid(any());

        assertThrows(InvalidHorseRaceStateException.class, () -> createBetForUserUseCase.execute(lowCreateBetDTO));

    }

    @Test
    public void createBetSuccessfully() {
        user.setBalance(new BigDecimal(10000));

        doReturn(user)
                .when(userService)
                .findOneByUuid(any());

        doReturn(scheduledHorseRace)
                .when(horseRaceService)
                .findByParticipantUuid(any());

        doReturn(createBetEntityDTO)
                .when(betApplicationMapper)
                .toCreateEntityDTO(any());

        doReturn(bet)
                .when(betRepository)
                .create(any());

        doReturn(user)
                .when(userService)
                .subtractFunds(any());

        assertEquals(bet, createBetForUserUseCase.execute(lowCreateBetDTO));
    }
}
