package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.config.MoneyConfig;
import com.johannag.tapup.bets.application.useCases.stubs.BetStubs;
import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.application.utils.MoneyUtils;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModelState;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GenerateBetInfoForHorseRaceUseCaseTests {

    @Mock
    private BetRepository betRepository;

    @Mock
    private HorseRaceService horseRaceService;

    @Mock
    private BetConfig betConfig;

    @Mock
    private MoneyConfig moneyConfig;

    @Mock
    private MoneyUtils moneyUtils;

    @Spy
    @InjectMocks
    private GenerateBetInfoForHorseRaceUseCase generateBetInfoUseCase;

    private final HorseRaceModel horseRace = HorseRaceStubs.horseRaceStub(HorseRaceModelState.SCHEDULED);
    private final List<BetSummaryDTO> betSummaryDTOS = BetStubs.betSummaryDTO();
    private final List<BetSummaryModel> betSummaryModels = BetStubs.betSummaryModels();

    @Test
    public void horseRaceNotFound(){
        HorseRaceNotFoundException exception = new  HorseRaceNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(horseRaceService)
                .findOneByUuid(any());

        assertThrows(HorseRaceNotFoundException.class, () -> generateBetInfoUseCase.execute(UUID.randomUUID()));

    }

    @Test
    public void betInfoGeneratedSuccessfully(){

        doReturn(horseRace)
                .when(horseRaceService)
                .findOneByUuid(any());

        doReturn(betSummaryDTOS)
                .when(betRepository)
                .findBetDetails(any());

        doReturn(1.1)
                .when(betConfig)
                .getMinOdds();

        doReturn(new BigDecimal("1.1"))
                .when(moneyUtils)
                .toBigDecimal(1.1);

        doReturn(2)
                .when(moneyConfig)
                .getScale();

        assertEquals(betSummaryModels, generateBetInfoUseCase.execute(UUID.randomUUID()));
    }
}
