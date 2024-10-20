package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
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
public class ValidateExistUsersUseCase {

    private static final Logger logger = Logger.getLogger(ValidateExistUsersUseCase.class);
    private final UserRepository userRepository;

    public void execute(Collection<UUID> userUuids) throws UserNotFoundException {
        logger.info("Starting validateExistsUsers process");

        Set<UUID> allUserUuidsInSystem = fetchAllUsersUuidsFromSystem(userUuids);
        validateIfAllUsersWhereFoundOrThrow(allUserUuidsInSystem, userUuids);

        logger.info("Finish validateExistsUsers process");
    }

    private Set<UUID> fetchAllUsersUuidsFromSystem(Collection<UUID> userUuids) {
       return userRepository
                .findAll(userUuids).stream()
                .map(UserModel::getUuid)
                .collect(Collectors.toSet());
    }

    private void validateIfAllUsersWhereFoundOrThrow(Set<UUID> allUserUuidsInSystem, Collection<UUID> userUuids) throws UserNotFoundException {
        List<UUID> uuidsNotFound = userUuids.stream()
                .filter(uuid -> !allUserUuidsInSystem.contains(uuid))
                .toList();

        if (!uuidsNotFound.isEmpty()) {
            throw new UserNotFoundException(userUuids);
        }
    }
}
