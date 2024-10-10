package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.tokens.application.services.TokenService;
import com.johannag.tapup.tokens.domain.models.TokenModel;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithTokenModel;
import com.johannag.tapup.utils.PasswordUtils;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogInUserUseCase {

    private static final Logger logger = LogManager.getLogger(LogInUserUseCase.class);
    private TokenService tokenService;
    private FindUserByEmailUseCase findUserByEmailUseCase;

    public UserWithTokenModel execute(LogInUserDTO dto) throws InvalidLoginCredentialsException {
        logger.info("Starting LogIn process for user [{}]", dto.getEmail());

        UserModel user = retrieveUserWithValidCredentialsOrThrow(dto);
        TokenModel token = tokenService.createJWT(user);
        UserWithTokenModel userWithToken = buildUserWithToken(user, token);

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
        if (!PasswordUtils.match(password, user.getHashedPassword())) {
            throw new InvalidLoginCredentialsException();
        }
    }

    private UserWithTokenModel buildUserWithToken(UserModel user, TokenModel token) {
        return UserWithTokenModel.builder()
                .user(user)
                .token(token)
                .build();
    }

}
