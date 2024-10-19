package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.config.BetConfig;
import com.johannag.tapup.bets.application.config.MoneyConfig;
import com.johannag.tapup.bets.application.exceptions.UnexpectedPaymentException;
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
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProcessPaymentsUseCase {

    private static final Logger logger = Logger.getLogger(ProcessPaymentsUseCase.class);
    private final HorseRaceService horseRaceService;
    private final UserService userService;
    private final BetRepository betRepository;
    private final GenerateBetStatisticsForHorseRacesUseCase generateBetStatisticsForHorseRacesUseCase;
    private final BetConfig betConfig;
    private final MoneyConfig moneyConfig;

    public BetPayouts execute(UUID horseRaceUuid) throws HorseRaceNotFoundException,
            InvalidHorseRaceStateException {
        logger.info("Starting processPayments process for horseRace with Uuid {}", horseRaceUuid);

        HorseRaceModel horseRace = horseRaceService.findOneByUuid(horseRaceUuid);
        validateHorseRaceStateIsValidOrThrow(horseRace);
        ParticipantModel winner = obtainWinnerOrThrow(horseRace);
        BetStatisticsModel betStatistics = generateBetStatisticsForHorseRacesUseCase.execute(horseRaceUuid);
        processInBatch(winner, betStatistics);

        logger.info("Finished processPayments process for horseRace with Uuid {}", horseRaceUuid);
        return null;
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

    private void processInBatch(ParticipantModel winner, BetStatisticsModel betStatistics) {
        double odds = getOddsForHorseOrThrow(winner.getHorseUuid(), betStatistics);
        long winningBets = getTotalBetsForHorseOrThrow(winner.getHorseUuid(), betStatistics);

        BetPayoutsDTO betPayoutsDTO = iterateBatches(winner, betConfig.getBatchSize(), odds);

        BetPayouts betPayouts = BetPayouts.builder()
                .totalBets(betStatistics.getTotalBets())
                .totalWinningBets(winningBets)
                .totalPayouts(betPayoutsDTO.getTotalPayouts())
                .totalAmount(betPayoutsDTO.getTotalAmount())
                .build();

        logger.info("Process payments batch has finished. Results: {}", betPayouts.toString());
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

    private BetPayoutsDTO iterateBatches(ParticipantModel winner, int batchSize, double odds) {
        int currentPage = 0;
        long totalPayout = 0;
        BigDecimal totalAmount = new BigDecimal(0);
        Page<BetModel> betsToPay;

        //TODO pasar las bets a paid

        try {
            do {
                betsToPay = betRepository.findBetsByParticipantUuid(winner.getUuid(), currentPage, batchSize);

                if (betsToPay.hasContent()) {
                    List<AddUserFundsDTO> dtos = transformToAddUserFundsDTO(betsToPay, odds);
                    BigDecimal amountProcessed = dtos.stream()
                            .map(AddUserFundsDTO::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    userService.addFunds(dtos);
                    totalPayout += betsToPay.getContent().size();
                    totalAmount = totalAmount.add(amountProcessed);
                    currentPage++;
                }

            } while (betsToPay.hasContent());
        } catch (Exception e) {
            logger.error("An error occurred while processing bets for participant {}. Error: {}", winner.getUuid(),
                    e.getMessage());
        }

        return BetPayoutsDTO.builder()
                .totalAmount(totalAmount)
                .totalPayouts(totalPayout)
                .build();
    }

    private List<AddUserFundsDTO> transformToAddUserFundsDTO(Page<BetModel> bets, double odds) {

        BigDecimal oddsAsBigDecimal = BigDecimal.valueOf(odds).setScale(moneyConfig.getScale(), RoundingMode.HALF_UP);

        Map<UUID, BigDecimal> amountsByUserUuidMap = bets.stream()
                .collect(Collectors.toMap(bet -> bet.getUser().getUuid(), BetModel::getAmount, BigDecimal::add));

        return amountsByUserUuidMap.entrySet().stream()
                .map(entry -> new AddUserFundsDTO(entry.getKey(), entry.getValue().multiply(oddsAsBigDecimal)))
                .toList();
    }

}
