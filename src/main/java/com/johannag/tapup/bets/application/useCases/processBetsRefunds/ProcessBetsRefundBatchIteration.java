package com.johannag.tapup.bets.application.useCases.processBetsRefunds;

import com.johannag.tapup.bets.application.cache.BetsRefundCache;
import com.johannag.tapup.bets.application.dtos.ProcessRefundBatchDTO;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.domain.dtos.UpdateBetEntitiesStateDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.domain.models.BetRefundsCache;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.application.utils.MoneyUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.services.NotificationService;
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.dtos.SubtractUserFundsDTO;
import com.johannag.tapup.users.application.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.johannag.tapup.bets.application.constants.BetNotificationConstant.*;
import static com.johannag.tapup.notifications.domain.models.NotificationModelType.INFO;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProcessBetsRefundBatchIteration {

    private static final Logger logger = Logger.getLogger(ProcessBetsRefundBatchIteration.class);
    private final UserService userService;
    private final BetRepository betRepository;
    private final BetApplicationMapper betApplicationMapper;
    private final NotificationService notificationService;
    private final BetsRefundCache betsRefundCache;
    private final MoneyUtils moneyUtils;

    @Transactional
    public void execute(ProcessRefundBatchDTO dto) {
        logger.info("Starting to process batch iteration: {}", dto.currentPage());

        List<BetModel> bets = dto.getBets().getContent();
        List<AddUserFundsDTO> addUserFundsDTOs = mapToAddUserFundsDTO(bets);
        UpdateBetEntitiesStateDTO updateBetsStateDTO = mapToUpdateBetDTO(bets);
        List<CreateNotificationDTO> createNotificationDTOS = mapToNotificationsDTOs(bets);

        userService.addFunds(addUserFundsDTOs);
        betRepository.updateState(updateBetsStateDTO);
        notificationService.create(createNotificationDTOS);
        updateCache(dto.getHorseRaceUuid(), bets);

        logger.info("Finished process batch iteration: {}", dto.currentPage());
    }

    private List<AddUserFundsDTO> mapToAddUserFundsDTO(List<BetModel> bets) {

        Collector<BetModel, ?, BigDecimal> sumAmountsCollector =
                Collectors.reducing(BigDecimal.ZERO, BetModel::getAmount, BigDecimal::add);

        return bets.stream()
                .collect(Collectors.groupingBy(BetModel::getUserUuid, sumAmountsCollector))
                .entrySet().stream()
                .map(entry -> new AddUserFundsDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    private UpdateBetEntitiesStateDTO mapToUpdateBetDTO(List<BetModel> bets){

        List<UUID> betUuids = bets.stream()
                .map(BetModel::getUuid)
                .toList();

        return new UpdateBetEntitiesStateDTO(betUuids, BetModelState.REFUND);
    }

    private List<CreateNotificationDTO> mapToNotificationsDTOs(List<BetModel> bets) {
        return bets.stream()
                .map(this::createNotificationDTO)
                .toList();
    }

    private CreateNotificationDTO createNotificationDTO(BetModel bet) {
        UUID userUuid = bet.getUserUuid();
        String message = String.format(REFUND_MSG, moneyUtils.toString(bet.getAmount()));
        return new CreateNotificationDTO(userUuid, message, INFO);
    }

    private void updateCache(UUID horseRaceUuid, List<BetModel> bets) {
        BetRefundsCache cacheData = betsRefundCache.getOrDefault(horseRaceUuid);
        cacheData.addAmount(obtainRefundAmount(bets));
        cacheData.addRefunds(bets.size());
        betsRefundCache.update(horseRaceUuid, cacheData);
    }

    private BigDecimal obtainRefundAmount(List<BetModel> bets) {
        return bets.stream()
                .map(BetModel::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
