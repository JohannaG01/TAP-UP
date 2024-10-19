package com.johannag.tapup.bets.application.useCases.payments;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.dtos.ProcessPaymentBatchDTO;
import com.johannag.tapup.bets.application.exceptions.UnexpectedPaymentException;
import com.johannag.tapup.bets.application.useCases.GenerateBetStatisticsForHorseRacesUseCase;
import com.johannag.tapup.bets.domain.dtos.BetPayoutsDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetPayouts;
import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.domain.models.ParticipantModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessPaymentsUseCase {

    private static final Logger logger = Logger.getLogger(ProcessPaymentsUseCase.class);
    private final HorseRaceService horseRaceService;
    private final BetRepository betRepository;
    private final GenerateBetStatisticsForHorseRacesUseCase generateBetStatisticsForHorseRacesUseCase;
    private final ProcessPaymentBatchIteration processPaymentBatchIteration;
    private final BetConfig betConfig;

    public BetPayouts execute(UUID horseRaceUuid) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException {
        logger.info("Starting processPayments process for horseRace with Uuid {}", horseRaceUuid);

        HorseRaceModel horseRace = horseRaceService.findOneByUuid(horseRaceUuid);
        validateHorseRaceStateIsValidOrThrow(horseRace);
        ParticipantModel winner = obtainWinnerOrThrow(horseRace);
        BetStatisticsModel betStatistics = generateBetStatisticsForHorseRacesUseCase.execute(horseRaceUuid);
        BetPayouts betPayouts = processPaymentsInBatch(horseRaceUuid, winner, betStatistics);

        logger.info("Finished processPayments process for horseRace with Uuid {}", horseRaceUuid);
        return betPayouts;
    }

    private void validateHorseRaceStateIsValidOrThrow(HorseRaceModel horseRace) throws InvalidHorseRaceStateException {
        if (!horseRace.isFinished()) {
            throw new InvalidHorseRaceStateException(String.format("Cannot process payments for horse race with uuid " +
                    "%s. State must be FINISHED", horseRace.getUuid()));
        }
    }

    private ParticipantModel obtainWinnerOrThrow(HorseRaceModel horseRace) throws UnexpectedPaymentException {
        return horseRace.getWinner()
                .orElseThrow(() -> new UnexpectedPaymentException(String.format("An unexpected error " +
                        "occurred while getting winner for horseRace uuid %s. No winner found", horseRace.getUuid())));
    }

    private BetPayouts processPaymentsInBatch(UUID horseRaceUuid, ParticipantModel winner,
                                              BetStatisticsModel betStatistics) {
        double odds = getOddsForHorseOrThrow(winner.getHorseUuid(), betStatistics);
        long winningBets = getTotalBetsForHorseOrThrow(winner.getHorseUuid(), betStatistics);

        BetPayoutsDTO betPayoutsDTO = iteratePaymentInBatches(horseRaceUuid, betConfig.getBatchSize(), odds);

        BetPayouts betPayouts = BetPayouts.builder()
                .totalBets(betStatistics.getTotalBets())
                .totalWinningBets(winningBets)
                .totalPayouts(betPayoutsDTO.getTotalPayouts())
                .totalAmount(betPayoutsDTO.getTotalAmount())
                .build();

        logger.info("Process payments batch has finished. Results: {}", betPayouts.toString());
        return betPayouts;
    }

    private double getOddsForHorseOrThrow(UUID horseUuid, BetStatisticsModel betStatistics) throws UnexpectedPaymentException {
        return betStatistics.getOdds(horseUuid)
                .orElseThrow(() -> new UnexpectedPaymentException(String.format("An unexpected error occurred while " +
                        "getting odds for bets. Cannot find horse uuid %s in statistics for horse race", horseUuid)));
    }

    private long getTotalBetsForHorseOrThrow(UUID horseUuid, BetStatisticsModel betStatistics) {
        return betStatistics.getTotalBetsByHorseUuid(horseUuid)
                .orElseThrow(() -> new UnexpectedPaymentException(String.format("An unexpected error occurred while " +
                        "getting totalBets. Cannot find horse uuid %s in statistics for horse race", horseUuid)));
    }

    private BetPayoutsDTO iteratePaymentInBatches(UUID horseRaceUuid, int batchSize, double odds) {
        int currentPage = 0;
        BetPayoutsDTO betPayoutsDTO;
        Page<BetModel> betsToPay;
        BigDecimal totalAmount = new BigDecimal(0);
        long totalPayouts = 0;

        try {
            do {
                betsToPay = betRepository.findBetsByHorseRaceUuid(horseRaceUuid, currentPage, batchSize);

                if (betsToPay.hasContent()) {
                    betPayoutsDTO = processPaymentBatchIteration.execute(new ProcessPaymentBatchDTO(betsToPay, odds));
                    totalAmount = totalAmount.add(betPayoutsDTO.getTotalAmount());
                    totalPayouts += betPayoutsDTO.getTotalPayouts();
                    currentPage++;
                }

            } while (betsToPay.hasContent());
        } catch (Exception e) {
            logger.error("An error occurred while processing bets for horseRace {}. Error: {}", horseRaceUuid,
                    e.getMessage());
        }

        return BetPayoutsDTO.builder()
                .totalAmount(totalAmount)
                .totalPayouts(totalPayouts)
                .build();
    }
}
