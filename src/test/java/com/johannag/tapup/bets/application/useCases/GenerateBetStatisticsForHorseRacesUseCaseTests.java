package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.useCases.stubs.BetStubs;
import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GenerateBetStatisticsForHorseRacesUseCaseTests {

    @Mock
    private GenerateBetInfoForHorseRaceUseCase generateBetInfoForHorseRaceUseCase;

    @Spy
    @InjectMocks
    private GenerateBetStatisticsForHorseRacesUseCase generateBetStatisticsForHorseRacesUseCase;

    private final List<BetSummaryModel> betSummaries = BetStubs.betSummaryModels();
    private final BetStatisticsModel betStatistics = BetStubs.betStatisticsModel();

    @Test
    public void horseRaceNotFound() {
        HorseRaceNotFoundException exception = new HorseRaceNotFoundException(UUID.randomUUID());

        doThrow(exception)
                .when(generateBetInfoForHorseRaceUseCase)
                .execute(any());

        assertThrows(HorseRaceNotFoundException.class,
                () -> generateBetStatisticsForHorseRacesUseCase.execute(UUID.randomUUID()));
    }

    @Test
    public void betStatisticsGeneratedSuccessfully() {

        doReturn(betSummaries)
                .when(generateBetInfoForHorseRaceUseCase)
                .execute(any());

        assertEquals(betStatistics, generateBetStatisticsForHorseRacesUseCase.execute(UUID.randomUUID()));

    }
}
