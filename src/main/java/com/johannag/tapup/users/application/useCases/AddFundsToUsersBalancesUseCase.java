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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddFundsToUsersBalancesUseCase {

    private static final Logger logger = Logger.getLogger(AddFundsToUsersBalancesUseCase.class);
    private final UserRepository userRepository;
    private final UserApplicationMapper userApplicationMapper;

    public List<UserModel> execute(List<AddUserFundsDTO> dtos) {
        logger.info("Starting addFunds process for users");

        List<UUID> uuids = dtos.stream().map(AddUserFundsDTO::getUserUuid).toList();
        validateExistsAllUsersOrThrow(uuids);
        List<AddUserFundsToEntityDTO> addFundsDTOs = userApplicationMapper.toAddFundsToEntitiesDTO(dtos);
        List<UserModel> users = userRepository.addFunds(addFundsDTOs);

        logger.info("Finished addFunds process for users");
        return users;
    }

    private void validateExistsAllUsersOrThrow(Collection<UUID> usersUuids){

        List<UserModel> allUsers = userRepository.findAll(usersUuids);
        Set<UUID> allUserUuids = allUsers.stream()
                .map(UserModel::getUuid)
                .collect(Collectors.toSet());

        List<UUID> uuidsNotFound = usersUuids.stream()
                .filter(uuid -> !allUserUuids.contains(uuid))
                .toList();

        if (!uuidsNotFound.isEmpty()) {
            throw new UserNotFoundException(usersUuids);
        }
    }
}
