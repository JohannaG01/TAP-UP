package com.johannag.tapup.users.application.services;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.useCases.LogInUserUseCase;
import com.johannag.tapup.users.application.useCases.SignInUserUseCase;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithTokenModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final SignInUserUseCase signInUserUseCase;
    private final LogInUserUseCase logInUserUseCase;

    @Override
    public UserModel signIn(CreateUserDTO dto) throws UserAlreadyExistsException {
        return signInUserUseCase.execute(dto);
    }

    @Override
    public UserWithTokenModel logIn(LogInUserDTO dto) throws InvalidLoginCredentialsException {
        return logInUserUseCase.execute(dto);
    }
}
