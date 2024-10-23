package com.johannag.tapup.auth.application.useCases.stubs;

import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.globals.application.utils.DateTimeUtils;

public class AuthTokenStubs {

    public static AuthTokenModel authTokenModel(){
        return AuthTokenModel.builder()
                .value("token_value")
                .type("bearer")
                .issuedAt(DateTimeUtils.nowAsLocalDateTime())
                .expiresAt(DateTimeUtils.nowAsLocalDateTime().plusDays(1))
                .build();
    }
}
