package com.johannag.tapup.users.presentation.dtos.responses;

import com.johannag.tapup.auth.presentation.dtos.responses.AuthTokenResponseDTO;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class UserWithAuthTokenResponseDTO {
    AuthTokenResponseDTO token;
    UserResponseDTO user;
}
