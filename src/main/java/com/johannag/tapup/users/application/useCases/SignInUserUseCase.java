package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignInUserUseCase {

    private UserRepository userRepository;
    private UserApplicationMapper userApplicationMapper;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger LOGGER = LogManager.getLogger(SignInUserUseCase.class);

    public UserModel execute(CreateUserDTO dto) throws UserAlreadyExistsException {
        LOGGER.info("Starting SignIn process for user {}", dto.getEmail());

        validateUserDoesNotExistsOrThrow(dto.getEmail());
        UserModel user = createModelWithHashedPassword(dto);
        userRepository.create(user);

        LOGGER.info("SignIn process for user {} has finished", dto.getEmail());

        return user;
    }

    private void validateUserDoesNotExistsOrThrow(String email) throws UserAlreadyExistsException {
        LOGGER.info("Checking if user exists with email {}", email);

        if (userRepository.userExists(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }

    private UserModel createModelWithHashedPassword(CreateUserDTO dto) {
        LOGGER.info("Creating hash for password");

        UserModel user = userApplicationMapper.toUserModel(dto);
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));

        return user;
    }
}
