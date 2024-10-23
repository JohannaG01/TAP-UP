package com.johannag.tapup.bets.application.useCases.processBetsPayment;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.dtos.ProcessPaymentBatchDTO;
import com.johannag.tapup.bets.application.useCases.GenerateBetStatisticsForHorseRacesUseCase;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.application.BatchProcessor;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessBetsPaymentUseCase {

    private static final Logger logger = Logger.getLogger(ProcessBetsPaymentUseCase.class);
    private final HorseRaceService horseRaceService;
    private final BetRepository betRepository;
    private final GenerateBetStatisticsForHorseRacesUseCase generateBetStatisticsForHorseRacesUseCase;
    private final ProcessBetsPaymentBatchIteration processBetsPaymentBatchIteration;
    private final BetConfig betConfig;

    public void execute(UUID horseRaceUuid) throws HorseRaceNotFoundException, InvalidHorseRaceStateException {
        logger.info("Starting processPayments for horseRace with Uuid {}", horseRaceUuid);

        HorseRaceModel horseRace = horseRaceService.findOneByUuid(horseRaceUuid);
        validateHorseRaceStateIsValidOrThrow(horseRace);
        //TODO change this for calculateOdds useCase
        BetStatisticsModel betStatistics = generateBetStatisticsForHorseRacesUseCase.execute(horseRaceUuid);
        processPaymentsInBatch(horseRace, betStatistics);

        logger.info("Finished processPayments for horseRace with Uuid {}", horseRaceUuid);
    }

    private void validateHorseRaceStateIsValidOrThrow(HorseRaceModel horseRace) throws InvalidHorseRaceStateException {
        if (!horseRace.isFinished()) {
            throw new InvalidHorseRaceStateException(String.format("Cannot process payments for horse race with uuid " +
                    "%s. State must be FINISHED", horseRace.getUuid()));
        }
    }

    private void processPaymentsInBatch(HorseRaceModel horseRace,
                                        BetStatisticsModel betStatistics) {

        double odds = betStatistics.getOddsByHorseUuid(horseRace.getWinnerHorseUuid());
        iteratePaymentInBatches(horseRace.getUuid(), odds);
    }

    private void iteratePaymentInBatches(UUID horseRaceUuid, double odds) {
        int batchSize = betConfig.getBatchSize();

        BatchProcessor<BetModel> batchProcessor = new BatchProcessor<>(
                currentPage -> betRepository.findPendingBetsByHorseRaceUuid(horseRaceUuid, currentPage, batchSize),
                betsToPay -> processBetsPaymentBatchIteration
                        .execute(new ProcessPaymentBatchDTO(horseRaceUuid, betsToPay, odds))
        );

        batchProcessor.execute();
    }

}
