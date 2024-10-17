package com.johannag.tapup.bets.application.services;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.useCases.CreateBetForUserUseCase;
import com.johannag.tapup.bets.domain.models.BetModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BetServiceImpl implements BetService {

    private final CreateBetForUserUseCase createBetForUserUseCase;

    @Override
    public BetModel create(CreateBetDTO dto) {
        return createBetForUserUseCase.execute(dto);
    }
}
