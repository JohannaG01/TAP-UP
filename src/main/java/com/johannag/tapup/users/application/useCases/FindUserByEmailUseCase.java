package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindUserByEmailUseCase {

    private static final Logger logger = Logger.getLogger(FindUserByEmailUseCase.class);
    private final UserRepository userRepository;

    public UserModel execute(String email) throws UserNotFoundException {
        logger.debug("Starting FindUserByEmail process for email: [{}]", email);

        UserModel user = userRepository
                .findMaybeByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        logger.debug("Finished FindUserByEmail process for email: [{}]", email);
        return user;
    }
}
