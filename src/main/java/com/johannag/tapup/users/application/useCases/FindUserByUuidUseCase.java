package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.utils.Logger;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindUserByUuidUseCase {

    private static final Logger logger = Logger.getLogger(FindUserByUuidUseCase.class);
    private final UserRepository userRepository;

    public UserModel execute(UUID uuid) throws UserNotFoundException {
        logger.info("Finding user by UUID {}", uuid);

        return userRepository
                .findMaybeByUUID(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }
}
