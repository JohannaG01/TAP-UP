package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddFundsToUsersBalancesUseCase {

    private static final Logger logger = Logger.getLogger(AddFundsToUsersBalancesUseCase.class);
    private final UserRepository userRepository;
    private final UserApplicationMapper userApplicationMapper;
    private final ValidateExistUsersUseCase validateExistUsersUseCase;

    public List<UserModel> execute(List<AddUserFundsDTO> dtos) {
        logger.info("Starting addFunds process for {} users", dtos.size());

        List<UUID> uuids = dtos.stream().map(AddUserFundsDTO::getUserUuid).toList();
        validateExistUsersUseCase.execute(uuids);
        List<AddUserFundsToEntityDTO> addFundsDTOs = userApplicationMapper.toAddFundsToEntitiesDTO(dtos);
        List<UserModel> users = userRepository.addFunds(addFundsDTOs);

        logger.info("Finished addFunds process for {} users", dtos.size());
        return users;
    }
}
