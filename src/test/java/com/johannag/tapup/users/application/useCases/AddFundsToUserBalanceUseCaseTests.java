package com.johannag.tapup.users.application.useCases;

import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.application.useCases.stubs.UserStubs;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddFundsToUserBalanceUseCaseTests {

    @Mock private UserRepository userRepository;
    @Mock private UserApplicationMapper userApplicationMapper;
    @Mock private FindOneUserByUuidUseCase findOneUserByUuidUseCase;
    @InjectMocks @Spy private AddFundsToUserBalanceUseCase addFundsToUserBalanceUseCase;

    private final AddUserFundsDTO addUserFundsDTO = UserStubs.addUserFundsDTO();
    private final UserModel user = UserStubs.userModel();
    private final AddUserFundsToEntityDTO addUserFundsToEntityDTO = UserStubs.addUserFundsToEntityDTO();

    @Test
    public void userNotFound(){
        UserNotFoundException notFoundException = new UserNotFoundException(addUserFundsDTO.getUserUuid());

        doThrow(notFoundException)
                .when(findOneUserByUuidUseCase)
                .execute(any());

        assertThrows(UserNotFoundException.class, () -> addFundsToUserBalanceUseCase.execute(addUserFundsDTO));
    }

    @Test
    public void userFundsAddedSuccessfully(){

        doReturn(user)
                .when(findOneUserByUuidUseCase)
                .execute(any());

        doReturn(addUserFundsToEntityDTO)
                .when(userApplicationMapper)
                .toAddFundsToEntityDTO(any());

        doReturn(user)
                .when(userRepository)
                .addFunds(addUserFundsToEntityDTO);

        assertEquals(user, addFundsToUserBalanceUseCase.execute(addUserFundsDTO));
    }
}
