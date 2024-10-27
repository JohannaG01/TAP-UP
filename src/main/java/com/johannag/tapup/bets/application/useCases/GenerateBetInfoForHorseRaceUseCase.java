package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.config.MoneyConfig;
import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.application.utils.MoneyUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GenerateBetInfoForHorseRaceUseCase {

    private static final Logger logger = Logger.getLogger(GenerateBetInfoForHorseRaceUseCase.class);
    private final BetRepository betRepository;
    private final HorseRaceService horseRaceService;
    private final BetConfig betConfig;
    private final MoneyConfig moneyConfig;
    private final MoneyUtils moneyUtils;

    public List<BetSummaryModel> execute(UUID horseRaceUuid) {
        logger.info("Starting GenerateBetDetails process for Horse Race with UUID {}", horseRaceUuid);

        horseRaceService.findOneByUuid(horseRaceUuid);
        List<BetSummaryDTO> betSummaryDTOS = betRepository.findBetDetails(horseRaceUuid);
        List<BetSummaryModel> betSummaries = generateFullSummaries(betSummaryDTOS);

        logger.info("Finished GenerateBetDetails for Horse Race with UUID {}", horseRaceUuid);
        return betSummaries;
    }

    private List<BetSummaryModel> generateFullSummaries(List<BetSummaryDTO> partialBetSummaries) {
        Long maxTotalBets = calculateMaxTotalBets(partialBetSummaries);

        return partialBetSummaries.stream()
                .map(dto -> transformToSummaryWithOddsAndPayouts(maxTotalBets, dto))
                .toList();
    }

    private Long calculateMaxTotalBets(List<BetSummaryDTO> betSummaries) {
        return betSummaries.stream()
                .map(BetSummaryDTO::getTotalBets)
                .max(Long::compareTo)
                .orElse(0L);
    }

    private BetSummaryModel transformToSummaryWithOddsAndPayouts(Long maxTotalBets, BetSummaryDTO betSummaryDTO) {
        BetSummaryModel betSummary = new BetSummaryModel(betSummaryDTO, moneyConfig.getScale());

        BigDecimal odds = calculateOdds(maxTotalBets, betSummary.getTotalBets());
        BigDecimal totalPayouts = calculateTotalPayouts(odds, betSummaryDTO.getPaidBaseAmount());

        betSummary.setOdds(odds.doubleValue());
        betSummary.setTotalPayouts(totalPayouts);

        return betSummary;
    }

    private BigDecimal calculateOdds(Long maxTotalBets, Long bets) {
        double odds = betConfig.getMinOdds() + (maxTotalBets - bets) / (maxTotalBets + 1.0);
        return moneyUtils.toBigDecimal(odds);
    }

    private BigDecimal calculateTotalPayouts(BigDecimal odds, BigDecimal baseTotalPayouts) {
        return baseTotalPayouts
                .multiply(odds)
                .setScale(moneyConfig.getScale(), RoundingMode.HALF_UP);
    }
}

