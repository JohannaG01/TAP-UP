package com.johannag.tapup.authentication.application.services;

import com.johannag.tapup.authentication.application.exceptions.JwtTokenInvalidException;
import com.johannag.tapup.authentication.application.useCases.CreateJwtTokenUseCase;
import com.johannag.tapup.authentication.application.useCases.ValidateJwtTokenUseCase;
import com.johannag.tapup.authentication.domain.models.AuthTokenModel;
import com.johannag.tapup.users.domain.models.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CreateJwtTokenUseCase createJWTTokenUseCase;
    private final ValidateJwtTokenUseCase validateJwtTokenUseCase;

    @Override
    public AuthTokenModel createJwtToken(UserModel user) {
        return createJWTTokenUseCase.execute(user);
    }

    @Override
    public void validateJwtToken(String jwt) throws JwtTokenInvalidException {
        validateJwtTokenUseCase.execute(jwt);
    }
}
