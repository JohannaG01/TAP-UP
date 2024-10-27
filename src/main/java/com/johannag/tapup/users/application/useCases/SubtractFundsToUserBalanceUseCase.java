package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.dtos.SubtractUserFundsDTO;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.domain.dtos.SubtractUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubtractFundsToUserBalanceUseCase {
    private static final Logger logger = Logger.getLogger(SubtractFundsToUserBalanceUseCase.class);
    private final FindOneUserByUuidUseCase findOneUserByUuidUseCase;
    private final UserRepository userRepository;
    private final UserApplicationMapper userApplicationMapper;

    public UserModel execute(SubtractUserFundsDTO dto) throws UserNotFoundException {
        logger.info("Starting subtractFunds process for user {}", dto.getUserUuid());

        findOneUserByUuidUseCase.execute(dto.getUserUuid());
        SubtractUserFundsToEntityDTO subtractUserFundsEntityDTO = userApplicationMapper.toSubtractFundsToEntityDTO(dto);
        UserModel userModel = userRepository.subtractFunds(subtractUserFundsEntityDTO);

        logger.info("Finished subtractFunds process for user {}", dto.getUserUuid());
        return userModel;
    }

}
