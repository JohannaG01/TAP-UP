package com.johannag.tapup.bets.application.useCases;

import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.infrastructure.db.adapters.BetRepository;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindBetsForUserUseCase {

    private static final Logger logger = Logger.getLogger(FindBetsForUserUseCase.class);
    private final BetApplicationMapper betApplicationMapper;
    private final BetRepository betRepository;
    private final UserService userService;

    public Page<BetModel> execute(FindBetsDTO dto) {
        logger.info("Starting process for FindBets for User Uuid {}", dto.getUserUuid());

        userService.findOneByUuid(dto.getUserUuid());
        FindBetEntitiesDTO findBetEntitiesDTO = betApplicationMapper.toFindEntitiesDTO(dto);
        Page<BetModel> bets = betRepository.findAll(findBetEntitiesDTO);

        logger.info("Finished process for FindBets for User Uuid {}", dto.getUserUuid());
        return bets;
    }
}
