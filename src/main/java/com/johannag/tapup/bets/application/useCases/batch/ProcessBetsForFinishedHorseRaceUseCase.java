package com.johannag.tapup.bets.application.useCases.batch;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.dtos.ProcessPaymentBatchDTO;
import com.johannag.tapup.bets.application.useCases.GenerateBetStatisticsForHorseRacesUseCase;
import com.johannag.tapup.bets.domain.dtos.BetPayoutsDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetPayouts;
import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.application.useCases.BatchProcessor;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.notifications.application.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessBetsForFinishedHorseRaceUseCase {

    private static final Logger logger = Logger.getLogger(ProcessBetsForFinishedHorseRaceUseCase.class);
    private final HorseRaceService horseRaceService;
    private final BetRepository betRepository;
    private final NotificationService notificationService;
    private final GenerateBetStatisticsForHorseRacesUseCase generateBetStatisticsForHorseRacesUseCase;
    private final ProcessBetsForFinishedHorseRaceBatchIteration processBetsForFinishedHorseRaceBatchIteration;
    private final BetConfig betConfig;

    public BetPayouts execute(UUID horseRaceUuid) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException {
        logger.info("Starting processPayments process for horseRace with Uuid {}", horseRaceUuid);

        HorseRaceModel horseRace = horseRaceService.findOneByUuid(horseRaceUuid);
        validateHorseRaceStateIsValidOrThrow(horseRace);
        BetStatisticsModel betStatistics = generateBetStatisticsForHorseRacesUseCase.execute(horseRaceUuid);
        BetPayouts betPayouts = processPaymentsInBatch(horseRace, betStatistics);

        logger.info("Finished processPayments process for horseRace with Uuid {}", horseRaceUuid);
        return betPayouts;
    }

    private void validateHorseRaceStateIsValidOrThrow(HorseRaceModel horseRace) throws InvalidHorseRaceStateException {
        if (!horseRace.isFinished()) {
            throw new InvalidHorseRaceStateException(String.format("Cannot process payments for horse race with uuid " +
                    "%s. State must be FINISHED", horseRace.getUuid()));
        }
    }

    private BetPayouts processPaymentsInBatch(HorseRaceModel horseRace,
                                              BetStatisticsModel betStatistics) {

        double odds = betStatistics.getOddsByHorseUuid(horseRace.getWinnerHorseUuid());
        long winningBets = betStatistics.getTotalBetsByHorseUuid(horseRace.getWinnerHorseUuid());

        BetPayoutsDTO betPayoutsDTO = iteratePaymentInBatches(horseRace.getUuid(), odds);

        BetPayouts betPayouts = BetPayouts.builder()
                .totalBets(betStatistics.getTotalBets())
                .totalWinningBets(winningBets)
                .totalPayouts(betPayoutsDTO.getTotalPayouts())
                .totalAmount(betPayoutsDTO.getTotalAmount())
                .build();

        logger.info("Process payments batch has finished. Results: {}", betPayouts.toString());
        return betPayouts;
    }

    private BetPayoutsDTO iteratePaymentInBatches(UUID horseRaceUuid, double odds) {
        int batchSize = betConfig.getBatchSize();

        BatchProcessor<BetModel, BetPayoutsDTO> batchProcessor = new BatchProcessor<>(
                batchSize,
                currentPage -> betRepository.findPendingBetsByHorseRaceUuid(horseRaceUuid, currentPage, batchSize),
                betsToPay -> processBetsForFinishedHorseRaceBatchIteration.execute(new ProcessPaymentBatchDTO(betsToPay, odds))
        );

        return batchProcessor.execute();
    }

}
