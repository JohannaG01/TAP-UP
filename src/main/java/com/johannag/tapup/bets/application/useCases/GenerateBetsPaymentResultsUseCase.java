package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.cache.BetsPaymentCache;
import com.johannag.tapup.bets.domain.models.BetPayouts;
import com.johannag.tapup.bets.domain.models.BetPayoutsCache;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GenerateBetsPaymentResultsUseCase {

    private static final Logger logger = Logger.getLogger(GenerateBetsPaymentResultsUseCase.class);
    private final BetRepository betRepository;
    private final BetsPaymentCache betsPaymentCache;

    public BetPayouts execute(UUID horseRaceUuid){
        logger.info("Starting processBetsPaymentResults process for horse race uuid {}", horseRaceUuid);

        Long totalBets = betRepository.countBetsByHorseRaceUuid(horseRaceUuid);
        Long winningBets = betRepository.countWinningBetsByHorseRaceUuid(horseRaceUuid);
        BetPayoutsCache cache = betsPaymentCache.getOrDefault(horseRaceUuid);
        BetPayouts results = buildResults(totalBets, winningBets, cache);
        betsPaymentCache.evict(horseRaceUuid);

        logger.info("Finished processBetsPaymentResults process for horse race uuid {}", horseRaceUuid);

        return results;
    }

    private BetPayouts buildResults(Long totalBets, Long winningBets, BetPayoutsCache cache){
        return BetPayouts.builder()
                .totalBets(totalBets)
                .totalWinningBets(winningBets)
                .totalPayouts(cache.getTotalPayouts())
                .totalAmount(cache.getTotalAmount())
                .build();
    }
}
