package com.johannag.tapup.bets.application.useCases.payments;

import com.johannag.tapup.bets.application.config.MoneyConfig;
import com.johannag.tapup.bets.application.dtos.ProcessPaymentBatchDTO;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.services.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
class ProcessPaymentBatchIteration {

    private static final Logger logger = Logger.getLogger(ProcessPaymentBatchIteration.class);
    private final UserService userService;
    private final BetRepository betRepository;
    private final BetApplicationMapper betApplicationMapper;
    private final MoneyConfig moneyConfig;

    ProcessPaymentBatchIteration(UserService userService,
                                 BetRepository betRepository,
                                 BetApplicationMapper betApplicationMapper,
                                 MoneyConfig moneyConfig) {
        this.userService = userService;
        this.betRepository = betRepository;
        this.betApplicationMapper = betApplicationMapper;
        this.moneyConfig = moneyConfig;
    }

    @Transactional
    public BigDecimal execute(ProcessPaymentBatchDTO dto) {

        logger.info("Starting to process batch for process payments number: {}", dto.currentPage());

        Page<BetModel> bets = dto.getBets();
        List<AddUserFundsDTO> dtos = transformToAddUserFundsDTO(bets, dto.getOdds());

        BigDecimal amountProcessed = dtos.stream()
                .map(AddUserFundsDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        userService.addFunds(dtos);
        betRepository.updateState(betApplicationMapper.toUpdateEntityStateDTO(bets.getContent()));

        logger.info("Finished process batch for process payments number: {}", dto.currentPage());

        return amountProcessed;

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
