package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GenerateBetDetailsForHorseRaceUseCase {

    private static final Logger logger = Logger.getLogger(GenerateBetDetailsForHorseRaceUseCase.class);
    private final BetRepository betRepository;
    private final HorseRaceService horseRaceService;
    private final BetConfig betConfig;

    public List<BetSummaryModel> execute(UUID horseRaceUuid) {
        logger.info("Starting GenerateBetDetails process for Horse Race with UUID {}", horseRaceUuid);

        horseRaceService.findOneByUuid(horseRaceUuid);
        List<BetSummaryModel> betSummaryModels = betRepository.findBetDetails(horseRaceUuid);
        calculateOdds(betSummaryModels);

        logger.info("Finished GenerateBetDetails for Horse Race with UUID {}", horseRaceUuid);
        return betSummaryModels;
    }

    private void calculateOdds(List<BetSummaryModel> betSummaries) {
        Integer maxTotalBets = calculateMaxTotalBets(betSummaries);
        betSummaries.forEach(betSummary -> betSummary.calculateOdds(maxTotalBets, betConfig.getMinOdds()));
    }

    private Integer calculateMaxTotalBets(List<BetSummaryModel> betSummaries) {
        return betSummaries.stream()
                .map(BetSummaryModel::getTotalBets)
                .max(Integer::compareTo)
                .orElse(0);
    }
}

