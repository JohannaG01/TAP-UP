package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddFundsToUserBalanceUseCase {

    private static final Logger logger = Logger.getLogger(AddFundsToUserBalanceUseCase.class);
    private final FindOneUserByUuidUseCase findOneUserByUuidUseCase;
    private final UserRepository userRepository;
    private final UserApplicationMapper userApplicationMapper;

    public UserModel execute(AddUserFundsDTO dto) throws UserNotFoundException {
        logger.info("Starting addFunds process for user {}", dto.getUserUuid());

        findOneUserByUuidUseCase.execute(dto.getUserUuid());
        AddUserFundsToEntityDTO addUserFundsToEntityDTO = userApplicationMapper.toAddFundsToEntityDTO(dto);
        UserModel userModel = userRepository.addFunds(addUserFundsToEntityDTO);

        logger.info("Finished addFunds process for user {}", dto.getUserUuid());
        return userModel;
    }

}
