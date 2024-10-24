package com.johannag.tapup.bets.application.useCases.stubs;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.domain.dtos.BetSummaryDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.horseRaces.application.useCases.stubs.HorseRaceStubs;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.users.application.useCases.stubs.UserStubs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class BetStubs {

    public static CreateBetDTO createBetDTO(BigDecimal amount) {
        return CreateBetDTO.builder()
                .userUuid(UUID.randomUUID())
                .participantUuid(UUID.randomUUID())
                .amount(amount)
                .build();
    }

    public static CreateBetEntityDTO createBetEntityDTO() {
        return CreateBetEntityDTO.builder()
                .uuid(UUID.randomUUID())
                .userUuid(UUID.randomUUID())
                .participantUuid(UUID.randomUUID())
                .amount(new BigDecimal(500))
                .state(BetModelState.PENDING)
                .build();
    }

    public static BetModel betModel() {
        return BetModel.builder()
                .uuid(UUID.randomUUID())
                .user(UserStubs.userModel())
                .participant(null)
                .amount(new BigDecimal(500))
                .state(BetModelState.PENDING)
                .build();
    }

    public static FindBetsDTO findBetsDTO() {
        return FindBetsDTO.builder()
                .userUuid(UUID.randomUUID())
                .size(10)
                .page(0)
                .build();
    }

    public static FindBetEntitiesDTO findBetEntitiesDTO() {
        return FindBetEntitiesDTO.builder()
                .userUuid(UUID.randomUUID())
                .size(10)
                .page(0)
                .build();
    }

    public static Page<BetModel> betModelsPage() {
        return new PageImpl<>(List.of(betModel()), PageRequest.of(0,1), 1);
    }

    public static List<BetSummaryDTO> betSummaryDTO() {
        BetSummaryDTO betSummaryDTO = BetSummaryDTO.builder()
                .horse(new HorseModel())
                .totalBets(0L)
                .totalWagered(BigDecimal.ZERO)
                .paidBaseAmount(BigDecimal.ZERO)
                .build();

        return List.of(betSummaryDTO);
    }

    public static List<BetSummaryModel> betSummaryModels(){
        BigDecimal value = BigDecimal.ZERO;
        BigDecimal scaledValue = value.setScale(2, RoundingMode.HALF_UP);

        BetSummaryModel betSummaryModel = BetSummaryModel.builder()
                .horse(new HorseModel())
                .totalBets(0L)
                .totalAmountWagered(scaledValue)
                .totalPayouts(scaledValue)
                .odds(1.1)
                .build();

        return List.of(betSummaryModel);
    }

    public static BetStatisticsModel betStatisticsModel(){
        BigDecimal value = BigDecimal.ZERO;
        BigDecimal scaledValue = value.setScale(2, RoundingMode.HALF_UP);

        return BetStatisticsModel.builder()
                .totalBets(0L)
                .totalAmountWagered(scaledValue)
                .totalPayouts(scaledValue)
                .bets(betSummaryModels())
                .build();
    }
}
