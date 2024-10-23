package com.johannag.tapup.bets.application.useCases.processBetsRefunds;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.dtos.ProcessRefundBatchDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
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
public class ProcessBetRefundsUseCase {

    private static final Logger logger = Logger.getLogger(ProcessBetRefundsUseCase.class);
    private final HorseRaceService horseRaceService;
    private final BetRepository betRepository;
    private final ProcessBetsRefundBatchIteration processBetsRefundBatchIteration;
    private final BetConfig betConfig;

    public void execute(UUID horseRaceUuid) throws HorseRaceNotFoundException, InvalidHorseRaceStateException {
        logger.info("Starting processRefunds for horseRace with Uuid {}", horseRaceUuid);

        HorseRaceModel horseRace = horseRaceService.findOneByUuid(horseRaceUuid);
        validateHorseRaceStateIsValidOrThrow(horseRace);
        processPaymentsInBatch(horseRaceUuid);

        logger.info("Finished processRefunds for horseRace with Uuid {}", horseRaceUuid);
    }


    private void validateHorseRaceStateIsValidOrThrow(HorseRaceModel horseRace) throws InvalidHorseRaceStateException {
        if (!horseRace.isCancelled()) {
            throw new InvalidHorseRaceStateException(String.format("Cannot process refunds for horse race with uuid " +
                    "%s. State must be CANCELLED", horseRace.getUuid()));
        }
    }

    private void processPaymentsInBatch(UUID horseRaceUuid) {
        int batchSize = betConfig.getBatchSize();

        BatchProcessor<BetModel> batchProcessor = new BatchProcessor<>(
                currentPage -> betRepository.findPendingBetsByHorseRaceUuid(horseRaceUuid, currentPage, batchSize),
                bets -> processBetsRefundBatchIteration.execute(new ProcessRefundBatchDTO(horseRaceUuid, bets))
        );

        batchProcessor.execute();
    }

}
