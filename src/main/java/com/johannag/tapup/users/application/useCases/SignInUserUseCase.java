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

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LogManager.getLogger(SignInUserUseCase.class);
    private UserRepository userRepository;
    private UserApplicationMapper userApplicationMapper;

    public UserModel execute(CreateUserDTO dto) throws UserAlreadyExistsException {
        logger.info("Starting SignIn process for user {}", dto.getEmail());

        validateUserDoesNotExistsOrThrow(dto.getEmail());
        UserModel user = createModelWithHashedPassword(dto);
        userRepository.create(user);

        logger.info("SignIn process for user {} has finished", dto.getEmail());

        return user;
    }

    private void validateUserDoesNotExistsOrThrow(String email) throws UserAlreadyExistsException {
        logger.info("Checking if user exists with email {}", email);

        if (userRepository.userExists(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }

    private UserModel createModelWithHashedPassword(CreateUserDTO dto) {
        logger.info("Creating hash for password");

        UserModel user = userApplicationMapper.toUserModel(dto);
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));

        return user;
    }
}
