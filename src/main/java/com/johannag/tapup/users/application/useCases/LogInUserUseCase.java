package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.auth.application.services.AuthenticationService;
import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.globals.application.utils.PasswordUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogInUserUseCase {

    private static final Logger logger = Logger.getLogger(LogInUserUseCase.class);
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final FindUserByEmailUseCase findUserByEmailUseCase;

    public UserWithAuthTokenModel execute(LogInUserDTO dto) throws InvalidLoginCredentialsException {
        logger.info("Starting LogIn process for user [{}]", dto.getEmail());

        UserModel user = findUserByEmailOrThrow(dto.getEmail());
        validateMatchingPasswordOrThrow(user, dto.getPassword());
        Long id = userRepository.findUserIdByEmail(dto.getEmail());
        AuthTokenModel token = authenticationService.createJwtToken(id, user);
        UserWithAuthTokenModel userWithToken = buildUserWithToken(user, token);

        logger.info("Finished LogIn process for user [{}]", dto.getEmail());
        return userWithToken;
    }

    private UserModel findUserByEmailOrThrow(String email) throws InvalidLoginCredentialsException {
        try {
            return findUserByEmailUseCase.execute(email);
        } catch (UserNotFoundException ex) {
            throw new InvalidLoginCredentialsException();
        }
    }

    private void validateMatchingPasswordOrThrow(UserModel user, String password) throws InvalidLoginCredentialsException {
        logger.info("Validating credentials for user [{}]", user.getEmail());

        if (!PasswordUtils.match(password, user.getHashedPassword())) {
            throw new InvalidLoginCredentialsException();
        }
    }

    private UserWithAuthTokenModel buildUserWithToken(UserModel user, AuthTokenModel token) {
        return UserWithAuthTokenModel.builder()
                .user(user)
                .token(token)
                .build();
    }

}
