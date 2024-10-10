package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindUserByEmailUseCase {

    private static final Logger logger = LogManager.getLogger(FindUserByEmailUseCase.class);
    private UserRepository userRepository;

    public UserModel execute(String email) throws UserNotFoundException {
        logger.info("Starting FindUserByEmail process for email: [{}]", email);

        UserModel user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        logger.info("Finished FindUserByEmail process for email: [{}]", email);

        return user;
    }
}
