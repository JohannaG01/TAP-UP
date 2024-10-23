package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.application.useCases.stubs.UserStubs;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SignInUserUseCaseTests {

    @Mock private UserRepository userRepository;
    @Mock private UserApplicationMapper userApplicationMapper;
    @InjectMocks @Spy private SignInUserUseCase signInUserUseCase;

    private final CreateUserDTO createUserDTO = UserStubs.createUserDTO();
    private final CreateUserEntityDTO createUserEntityDTO = UserStubs.createUserEntityDTO();
    private final UserModel user = UserStubs.userModel();

    @Test
    public void userAlreadyExists(){
        UserAlreadyExistsException exception = new UserAlreadyExistsException(createUserDTO.getEmail());

        doThrow(exception)
                .when(userRepository)
                .existsUser(any());

        assertThrows(UserAlreadyExistsException.class, () -> signInUserUseCase.execute(createUserDTO));
    }

    @Test
    public void userSignInSuccessfully(){
        doReturn(false)
                .when(userRepository)
                .existsUser(any());

        doReturn(createUserEntityDTO)
                .when(userApplicationMapper)
                .toCreateEntityDTO(any(), any());

        doReturn(user)
                .when(userRepository)
                .create(any());

        assertEquals(user, signInUserUseCase.execute(createUserDTO));
    }
}
