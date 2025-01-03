package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.globals.application.utils.PasswordUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignInUserUseCase {

    private static final Logger logger = Logger.getLogger(SignInUserUseCase.class);
    private final UserRepository userRepository;
    private final UserApplicationMapper userApplicationMapper;

    public UserModel execute(CreateUserDTO dto) throws UserAlreadyExistsException {
        logger.info("Starting SignIn process for user {}", dto.getEmail());

        validateUserDoesNotExistsOrThrow(dto.getEmail());
        CreateUserEntityDTO createUserEntityDTO = buildCreationDTOWithHashedPassword(dto);
        UserModel user = userRepository.create(createUserEntityDTO);

        logger.info("SignIn process for user {} has finished", dto.getEmail());
        return user;
    }

    private void validateUserDoesNotExistsOrThrow(String email) throws UserAlreadyExistsException {
        logger.info("Checking if user exists with email {}", email);

        if (userRepository.existsUser(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }

    private CreateUserEntityDTO buildCreationDTOWithHashedPassword(CreateUserDTO dto) {
        logger.info("Creating hash for password");

        String hashedPassword = PasswordUtils.hash(dto.getPassword());
        CreateUserEntityDTO createUserEntityDTO = userApplicationMapper.toCreateEntityDTO(dto, hashedPassword);

        return createUserEntityDTO;
    }
}
