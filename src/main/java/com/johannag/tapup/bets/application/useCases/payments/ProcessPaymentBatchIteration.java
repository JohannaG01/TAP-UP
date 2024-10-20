package com.johannag.tapup.bets.application.useCases.payments;

import com.johannag.tapup.bets.application.config.MoneyConfig;
import com.johannag.tapup.bets.application.dtos.ProcessPaymentBatchDTO;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.domain.dtos.BetPayoutsDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.services.NotificationAsyncService;
import com.johannag.tapup.notifications.domain.models.NotificationModelType;
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ProcessPaymentBatchIteration {

    private static final Logger logger = Logger.getLogger(ProcessPaymentBatchIteration.class);
    private final UserService userService;
    private final BetRepository betRepository;
    private final BetApplicationMapper betApplicationMapper;
    private final NotificationAsyncService notificationAsyncService;
    private final MoneyConfig moneyConfig;

    @Transactional
    public BetPayoutsDTO execute(ProcessPaymentBatchDTO dto) {

        logger.info("Starting to process batch for process payments number: {}", dto.currentPage());

        Page<BetModel> bets = dto.getBets();

        List<BetModel> winnerBets = bets.stream()
                .filter(BetModel::isWinner)
                .toList();

        List<AddUserFundsDTO> dtos = mapToAddUserFundsDTO(winnerBets, dto.getOdds());

        BigDecimal amountProcessed = dtos.stream()
                .map(AddUserFundsDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        userService.addFunds(dtos);
        betRepository.updateState(betApplicationMapper.toUpdateEntityStateDTO(bets.getContent()));
        List<CreateNotificationDTO> createNotificationDTOS = notifcationsDtos(bets, dtos);
        notificationAsyncService.createNotifications(createNotificationDTOS);

        logger.info("Finished process batch for process payments number: {}", dto.currentPage());

        return BetPayoutsDTO.builder()
                .totalAmount(amountProcessed)
                .totalPayouts(winnerBets.size())
                .build();

    }

    private List<AddUserFundsDTO> mapToAddUserFundsDTO(List<BetModel> bets, double odds) {

        BigDecimal oddsAsBigDecimal = BigDecimal
                .valueOf(odds)
                .setScale(moneyConfig.getScale(), RoundingMode.HALF_UP);

        Map<UUID, BigDecimal> amountsByUserUuidMap = bets.stream()
                .collect(Collectors.toMap(bet -> bet.getUser().getUuid(), BetModel::getAmount, BigDecimal::add));

        return amountsByUserUuidMap.entrySet().stream()
                .map(entry -> new AddUserFundsDTO(entry.getKey(), entry.getValue().multiply(oddsAsBigDecimal)))
                .toList();
    }

    private List<CreateNotificationDTO> notifcationsDtos(Page<BetModel> bets, List<AddUserFundsDTO> dtos) {
        String winnersMessage = "Horse Race has finished. Congrats you have won the bet :)";
        String losersMessage = "Horse Race has finished. Unfortunately, you didn't won the bet :c";

        //ESTO ESTA MAL, UN USUARIO PUEDE SER WINNER Y LOSSER
        Set<UUID> winners = dtos.stream()
                .map(AddUserFundsDTO::getUserUuid)
                .collect(Collectors.toSet());

        List<UUID> allUsersUuid = bets.getContent().stream()
                .map(BetModel::getUserUuid)
                .toList();

        List<UUID> losersToNotify = allUsersUuid.stream()
                .filter(uuid -> !winners.contains(uuid))
                .toList();

        List<UUID> winnersToNotify = allUsersUuid.stream()
                .filter(winners::contains)
                .toList();

        List<CreateNotificationDTO> winnersNotifications = winnersToNotify.stream()
                .map(uuid -> new CreateNotificationDTO(uuid, winnersMessage, NotificationModelType.INFO))
                .toList();

        List<CreateNotificationDTO> losersNotifications = losersToNotify.stream()
                .map(uuid -> new CreateNotificationDTO(uuid, losersMessage, NotificationModelType.INFO))
                .toList();

        List<CreateNotificationDTO> allNotifications = new ArrayList<>();
        allNotifications.addAll(winnersNotifications);
        allNotifications.addAll(losersNotifications);

        return allNotifications;
    }
}
