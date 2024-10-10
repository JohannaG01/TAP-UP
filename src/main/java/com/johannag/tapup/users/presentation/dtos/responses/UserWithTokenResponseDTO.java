package com.johannag.tapup.users.presentation.dtos.responses;

import com.johannag.tapup.tokens.presentation.dtos.responses.TokenResponseDTO;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class UserWithTokenResponseDTO {
    TokenResponseDTO token;
    UserResponseDTO user;
}
