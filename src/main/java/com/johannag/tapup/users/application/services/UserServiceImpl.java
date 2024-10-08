package com.johannag.tapup.users.application.services;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.useCases.SignInUserUseCase;
import com.johannag.tapup.users.domain.models.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final SignInUserUseCase signInUserUseCase;

    @Override
    public UserModel signIn(CreateUserDTO dto) throws UserAlreadyExistsException {
        return signInUserUseCase.execute(dto);
    }
}
