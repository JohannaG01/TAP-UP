package com.johannag.tapup.auth.application.services;

import com.johannag.tapup.auth.application.exceptions.JwtTokenInvalidException;
import com.johannag.tapup.auth.application.useCases.CheckIfUserIdAdminUseCase;
import com.johannag.tapup.auth.application.useCases.CreateJwtTokenUseCase;
import com.johannag.tapup.auth.application.useCases.ValidateJwtTokenUseCase;
import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.users.domain.models.UserModel;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CreateJwtTokenUseCase createJWTTokenUseCase;
    private final ValidateJwtTokenUseCase validateJwtTokenUseCase;
    private final CheckIfUserIdAdminUseCase checkIfUserIdAdminUseCase;

    @Override
    public AuthTokenModel createJwtToken(Long id, UserModel user) {
        return createJWTTokenUseCase.execute(id, user);
    }

    @Override
    public Claims validateJwtToken(String jwt) throws JwtTokenInvalidException {
        return validateJwtTokenUseCase.execute(jwt);
    }

    @Override
    public boolean isAdminUser() {
        return checkIfUserIdAdminUseCase.execute();
    }
}
