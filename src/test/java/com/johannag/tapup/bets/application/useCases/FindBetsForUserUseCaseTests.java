package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.application.useCases.stubs.BetStubs;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
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
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindBetsForUserUseCaseTests {

    @Mock
    private BetApplicationMapper betApplicationMapper;

    @Mock
    private BetRepository betRepository;

    @Mock
    private UserService userService;

    @Spy
    @InjectMocks
    private FindBetsForUserUseCase findBetsForUserUseCase;

    private final UserModel user = UserStubs.userModel();
    private final FindBetsDTO findBetsDTO = BetStubs.findBetsDTO();
    private final FindBetEntitiesDTO findBetEntitiesDTO = BetStubs.findBetEntitiesDTO();
    private final Page<BetModel> bets = BetStubs.betModelsPage();

    @Test
    public void userNotFound(){
        UserNotFoundException exception = new UserNotFoundException(findBetsDTO.getUserUuid());

        doThrow(exception)
                .when(userService)
                .findOneByUuid(any());

        assertThrows(UserNotFoundException.class, () -> findBetsForUserUseCase.execute(findBetsDTO));

    }

    @Test
    public void betsFoundSuccessfully(){

        doReturn(user)
                .when(userService)
                .findOneByUuid(any());

        doReturn(findBetEntitiesDTO)
                .when(betApplicationMapper)
                .toFindEntitiesDTO(any());

        doReturn(bets)
                .when(betRepository)
                .findAll(any());

        assertEquals(bets, findBetsForUserUseCase.execute(findBetsDTO));
    }
}
