package com.johannag.tapup.users.presentation.dtos.responses;

import com.johannag.tapup.authentication.presentation.dtos.responses.TokenResponseDTO;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class UserWithAuthTokenResponseDTO {
    TokenResponseDTO token;
    UserResponseDTO user;
}
