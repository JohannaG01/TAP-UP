package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.authentication.application.services.AuthenticationService;
import com.johannag.tapup.authentication.domain.models.AuthTokenModel;
import com.johannag.tapup.globals.application.utils.PasswordUtils;
import com.johannag.tapup.globals.utils.Logger;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogInUserUseCase {

    private static final Logger logger = Logger.getLogger(LogInUserUseCase.class);
    private AuthenticationService authenticationService;
    private FindUserByEmailUseCase findUserByEmailUseCase;

    public UserWithAuthTokenModel execute(LogInUserDTO dto) throws InvalidLoginCredentialsException {
        logger.info("Starting LogIn process for user [{}]", dto.getEmail());

        UserModel user = retrieveUserWithValidCredentialsOrThrow(dto);
        AuthTokenModel token = authenticationService.createJwtToken(user);
        UserWithAuthTokenModel userWithToken = buildUserWithToken(user, token);

        logger.info("Finished LogIn process for user [{}]", dto.getEmail());

        return userWithToken;
    }

    private UserModel retrieveUserWithValidCredentialsOrThrow(LogInUserDTO dto) throws InvalidLoginCredentialsException {
        try {
            UserModel user = findUserByEmailUseCase.execute(dto.getEmail());
            validateMatchingPasswordOrThrow(user, dto.getPassword());
            return user;
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
