package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.cache.BetsPaymentCache;
import com.johannag.tapup.bets.application.cache.BetsRefundCache;
import com.johannag.tapup.bets.domain.models.BetPayouts;
import com.johannag.tapup.bets.domain.models.BetPayoutsCache;
import com.johannag.tapup.bets.domain.models.BetRefunds;
import com.johannag.tapup.bets.domain.models.BetRefundsCache;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GenerateBetsRefundResultsUseCase {

    private static final Logger logger = Logger.getLogger(GenerateBetsRefundResultsUseCase.class);
    private final BetRepository betRepository;
    private final BetsRefundCache betsRefundCache;

    public BetRefunds execute(UUID horseRaceUuid) {
        logger.info("Starting generateBetsRefundResults process for horse race uuid {}", horseRaceUuid);

        Long totalBets = betRepository.countBetsByHorseRaceUuid(horseRaceUuid);
        BetRefundsCache cache = betsRefundCache.getOrDefault(horseRaceUuid);
        BetRefunds results = buildResults(totalBets, cache);
        betsRefundCache.evict(horseRaceUuid);

        logger.info("Finished generateBetsRefundResults process for horse race uuid {}", horseRaceUuid);

        return results;
    }

    private BetRefunds buildResults(Long totalBets, BetRefundsCache cache) {
        return BetRefunds.builder()
                .totalBets(totalBets)
                .totalRefunds(cache.getTotalRefunds())
                .totalAmount(cache.getTotalAmount())
                .build();
    }
}
