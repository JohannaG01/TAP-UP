package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GenerateHorseRacesBetStatisticsUseCase {

    private static final Logger logger = Logger.getLogger(GenerateHorseRacesBetStatisticsUseCase.class);
    private final GenerateBetInfoForHorseRaceUseCase generateBetInfoForHorseRaceUseCase;

    public BetStatisticsModel execute(UUID horseRaceUuid) throws HorseRaceNotFoundException {
        logger.info("Starting generateBetStatistics process for Horse Race UUID {}", horseRaceUuid);

        List<BetSummaryModel> betSummaries = generateBetInfoForHorseRaceUseCase.execute(horseRaceUuid);
        BetStatisticsModel betStatistics = generateStatistics(betSummaries);

        logger.info("Finished generateBetStatistics process for Horse Race UUID {}", horseRaceUuid);
        return betStatistics;
    }

    private BetStatisticsModel generateStatistics(List<BetSummaryModel> betSummaries) {
        Long totalBets = calculateTotalBets(betSummaries);
        BigDecimal totalAmountWagered = calculateTotalAmountWagered(betSummaries);
        BigDecimal totalPayouts = calculateTotalPayouts(betSummaries);

        return BetStatisticsModel.builder()
                .totalBets(totalBets)
                .totalAmountWagered(totalAmountWagered)
                .totalPayouts(totalPayouts)
                .bets(betSummaries)
                .build();

    }

    private Long calculateTotalBets(List<BetSummaryModel> betSummaries) {
        return betSummaries.stream()
                .mapToLong(BetSummaryModel::getTotalBets)
                .sum();
    }

    private BigDecimal calculateTotalAmountWagered(List<BetSummaryModel> betSummaries) {
        return betSummaries.stream()
                .map(BetSummaryModel::getTotalAmountWagered)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalPayouts(List<BetSummaryModel> betSummaries) {
        return betSummaries.stream()
                .map(BetSummaryModel::getTotalPayouts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
