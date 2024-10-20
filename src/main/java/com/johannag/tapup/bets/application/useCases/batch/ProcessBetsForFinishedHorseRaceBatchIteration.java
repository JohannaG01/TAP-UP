package com.johannag.tapup.bets.application.useCases.batch;

import com.johannag.tapup.bets.application.dtos.ProcessPaymentBatchDTO;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.domain.dtos.BetPayoutsDTO;
import com.johannag.tapup.bets.domain.dtos.UpdateBetEntitiesStateDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.application.utils.MoneyUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.services.NotificationService;
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.johannag.tapup.bets.application.constants.BetNotificationConstant.LOSERS_MSG;
import static com.johannag.tapup.bets.application.constants.BetNotificationConstant.WINNERS_MSG;
import static com.johannag.tapup.bets.domain.models.BetModelState.LOST;
import static com.johannag.tapup.bets.domain.models.BetModelState.PAID;
import static com.johannag.tapup.notifications.domain.models.NotificationModelType.INFO;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ProcessBetsForFinishedHorseRaceBatchIteration {

    private static final Logger logger = Logger.getLogger(ProcessBetsForFinishedHorseRaceBatchIteration.class);
    private final UserService userService;
    private final BetRepository betRepository;
    private final BetApplicationMapper betApplicationMapper;
    private final NotificationService notificationService;
    private final MoneyUtils moneyUtils;

    @Transactional
    public BetPayoutsDTO execute(ProcessPaymentBatchDTO dto) {

        logger.info("Starting to process batch iteration: {}", dto.currentPage());

        List<BetModel> bets = dto.getBets().getContent();
        Map<Boolean, Map<UUID, List<BigDecimal>>> partitionedBets = partitionBetsByWinner(bets);
        Map<UUID, List<BigDecimal>> winnerBets = partitionedBets.get(true);
        Map<UUID, List<BigDecimal>> loserBets = partitionedBets.get(false);
        BigDecimal odds = moneyUtils.ToBigDecimal(dto.getOdds());

        List<AddUserFundsDTO> addUserFundsDTOs = mapToAddUserFundsDTO(winnerBets, odds);
        UpdateBetEntitiesStateDTO updateWinnerBetsDTO = mapToUpdateBetDTOs(winnerBetsUuids(bets), PAID);
        UpdateBetEntitiesStateDTO updateLoserBetsDTO = mapToUpdateBetDTOs(loserBetsUuids(bets), LOST);

        userService.addFunds(addUserFundsDTOs);
        betRepository.updateState(updateWinnerBetsDTO);
        betRepository.updateState(updateLoserBetsDTO);

        List<CreateNotificationDTO> createNotificationDTOS = mapToNotificationsDTOs(winnerBets, loserBets, odds);
        notificationService.createNotifications(createNotificationDTOS);

        logger.info("Finished process batch iteration: {}", dto.currentPage());

        //TODO change this for cache

        // Sumar todos los valores de las listas
        BigDecimal totalSum = winnerBets.values().stream()
                .flatMap(List::stream) // Aplanar las listas en un solo stream
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar todos los valores

        // Contar cuántas listas hay en el mapa
        int numberOfEntities = winnerBets.size();

        int totalElementsInLists = winnerBets.values().stream()
                .mapToInt(List::size) // Obtener el tamaño de cada lista
                .sum(); // Sumar los tamaños

        // Multiplicar la suma total por el número de listas
        BigDecimal total = totalSum.multiply(BigDecimal.valueOf(numberOfEntities));

        return BetPayoutsDTO.builder()
                .totalAmount(total)
                .totalPayouts(totalElementsInLists)
                .build();

    }

    private Map<Boolean, Map<UUID, List<BigDecimal>>> partitionBetsByWinner(List<BetModel> bets) {

        Map<Boolean, Map<UUID, List<BigDecimal>>> result = new HashMap<>();
        result.put(true, new HashMap<>());
        result.put(false, new HashMap<>());

        for (BetModel bet : bets) {
            Map<UUID, List<BigDecimal>> betsByUser = result.get(bet.isWinner());
            betsByUser.computeIfAbsent(bet.getUserUuid(), k -> new ArrayList<>()).add(bet.getAmount());
        }

        return result;
    }

    private List<AddUserFundsDTO> mapToAddUserFundsDTO(Map<UUID, List<BigDecimal>> amountsByUserUuid,
                                                       BigDecimal odds) {
        List<AddUserFundsDTO> userFundsList = new ArrayList<>();

        for (Map.Entry<UUID, List<BigDecimal>> entry : amountsByUserUuid.entrySet()) {

            BigDecimal totalAmount = entry
                    .getValue().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            userFundsList.add(new AddUserFundsDTO(entry.getKey(), totalAmount.multiply(odds)));
        }

        return userFundsList;
    }

    private List<UUID> winnerBetsUuids(List<BetModel> bets) {
        return bets.stream()
                .filter(BetModel::isWinner)
                .map(BetModel::getUuid)
                .toList();
    }

    private List<UUID> loserBetsUuids(List<BetModel> bets) {
        return bets.stream()
                .filter(BetModel::isLoser)
                .map(BetModel::getUuid)
                .toList();
    }

    private UpdateBetEntitiesStateDTO mapToUpdateBetDTOs(List<UUID> betUuids, BetModelState state) {
        return betApplicationMapper.toUpdateEntityStateDTO(betUuids, state);
    }


    private List<CreateNotificationDTO> mapToNotificationsDTOs(Map<UUID, List<BigDecimal>> winnerBets,
                                                               Map<UUID, List<BigDecimal>> loserBets,
                                                               BigDecimal odds) {
        List<CreateNotificationDTO> winnersNotifications = mapToNotificationsDTOs(winnerBets, WINNERS_MSG, odds);
        List<CreateNotificationDTO> losersNotifications = mapToNotificationsDTOs(loserBets, LOSERS_MSG, BigDecimal.ONE);

        List<CreateNotificationDTO> allNotifications = new ArrayList<>();
        allNotifications.addAll(winnersNotifications);
        allNotifications.addAll(losersNotifications);

        return allNotifications;
    }

    private List<CreateNotificationDTO> mapToNotificationsDTOs(Map<UUID, List<BigDecimal>> bets,
                                                               String baseMessage,
                                                               BigDecimal multiplier) {
        List<CreateNotificationDTO> notifications = new ArrayList<>();

        for (Map.Entry<UUID, List<BigDecimal>> entry : bets.entrySet()) {

            List<CreateNotificationDTO> notificationsDTOs = entry
                    .getValue().stream()
                    .map(amount -> moneyUtils.toString(amount.multiply(multiplier)))
                    .map(amount -> new CreateNotificationDTO(entry.getKey(), String.format(baseMessage, amount), INFO))
                    .toList();

            notifications.addAll(notificationsDTOs);
        }

        return notifications;
    }
}
