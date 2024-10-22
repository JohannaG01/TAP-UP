package com.johannag.tapup.users.application.services;

import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.dtos.SubtractUserFundsDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.useCases.*;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final SignInUserUseCase signInUserUseCase;
    private final LogInUserUseCase logInUserUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final AddFundsToUserBalanceUseCase addFundsToUserBalanceUseCase;
    private final AddFundsToUsersBalancesUseCase addFundsToUsersBalancesUseCase;
    private final FindOneUserByUuidUseCase findOneUserByUuidUseCase;
    private final SubtractFundsToUserBalanceUseCase subtractFundsToUserBalanceUseCase;
    private final ValidateExistUsersUseCase validateExistUsersUseCase;
    private final FindAdminUsersUseCase findAllAdminUsersUseCase;

    @Override
    public UserModel signIn(CreateUserDTO dto) throws UserAlreadyExistsException {
        return signInUserUseCase.execute(dto);
    }

    @Override
    public UserWithAuthTokenModel logIn(LogInUserDTO dto) throws InvalidLoginCredentialsException {
        return logInUserUseCase.execute(dto);
    }

    @Override
    public UserModel findByEmail(String email) throws UserNotFoundException {
        return findUserByEmailUseCase.execute(email);
    }

    @Override
    public UserModel findOneByUuid(UUID uuid) throws UserNotFoundException {
        return findOneUserByUuidUseCase.execute(uuid);
    }

    @Override
    public UserModel addFunds(AddUserFundsDTO dto) throws UserNotFoundException {
        return addFundsToUserBalanceUseCase.execute(dto);
    }

    @Override
    public List<UserModel> addFunds(List<AddUserFundsDTO> dtos) throws UserNotFoundException {
        return addFundsToUsersBalancesUseCase.execute(dtos);
    }

    @Override
    public UserModel subtractFunds(SubtractUserFundsDTO dto) throws UserNotFoundException {
        return subtractFundsToUserBalanceUseCase.execute(dto);
    }

    @Override
    public void validateExistance(Collection<UUID> userUuid) throws UserNotFoundException {
        validateExistUsersUseCase.execute(userUuid);
    }

    @Override
    public List<UserModel> findAllAdmins() {
        return findAllAdminUsersUseCase.execute();
    }
}
