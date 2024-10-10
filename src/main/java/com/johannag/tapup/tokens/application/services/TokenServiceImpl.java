package com.johannag.tapup.tokens.application.services;

import com.johannag.tapup.tokens.application.useCases.CreateJWTTokenUseCase;
import com.johannag.tapup.tokens.domain.models.TokenModel;
import com.johannag.tapup.users.domain.models.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final CreateJWTTokenUseCase createJWTTokenUseCase;

    @Override
    public TokenModel createJWT(UserModel user) {
        return createJWTTokenUseCase.execute(user);
    }
}
