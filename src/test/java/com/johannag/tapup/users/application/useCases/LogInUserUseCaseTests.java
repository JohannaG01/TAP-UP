package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.auth.application.services.AuthenticationService;
import com.johannag.tapup.auth.application.useCases.stubs.AuthTokenStubs;
import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.globals.application.utils.PasswordUtils;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.application.useCases.stubs.UserStubs;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;
import com.johannag.tapup.users.infrastructure.db.adapter.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LogInUserUseCaseTests {

    @Mock private UserRepository userRepository;
    @Mock private  AuthenticationService authenticationService;
    @Mock private FindUserByEmailUseCase findUserByEmailUseCase;
    @InjectMocks @Spy private LogInUserUseCase logInUserUseCase;

    private final LogInUserDTO logInUserDTO = UserStubs.logInUserDTO();
    private final UserModel user = UserStubs.userModel();
    private final AuthTokenModel authToken = AuthTokenStubs.authTokenModel();
    private final UserWithAuthTokenModel userWithAuthToken = new UserWithAuthTokenModel(authToken, user);

    @Test
    public void userNotFound() {
        UserNotFoundException notFoundException = new UserNotFoundException(logInUserDTO.getEmail());

        doThrow(notFoundException)
                .when(findUserByEmailUseCase)
                .execute(any());

        assertThrows(InvalidLoginCredentialsException.class, () -> logInUserUseCase.execute(logInUserDTO));

    }

    @Test
    public void passwordDoesNotMatch() {

        doReturn(user)
                .when(findUserByEmailUseCase)
                .execute(any());

        assertThrows(InvalidLoginCredentialsException.class, () -> logInUserUseCase.execute(logInUserDTO));
    }

    @Test
    public void userLogInSuccessfully(){
        user.setHashedPassword(PasswordUtils.hash(logInUserDTO.getPassword()));

        doReturn(user)
                .when(findUserByEmailUseCase)
                .execute(any());

        doReturn(1L)
                .when(userRepository)
                .findUserIdByEmail(any());

        doReturn(authToken)
                .when(authenticationService)
                .createJwtToken(any(), any());

        assertEquals(userWithAuthToken, logInUserUseCase.execute(logInUserDTO));
    }

}
