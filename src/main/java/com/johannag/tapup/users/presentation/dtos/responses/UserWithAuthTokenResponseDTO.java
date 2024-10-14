package com.johannag.tapup.users.presentation.dtos.responses;

import com.johannag.tapup.auth.presentation.dtos.responses.AuthTokenResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value
@Builder(builderClassName = "Builder")
public class UserWithAuthTokenResponseDTO {
    @NotNull
    AuthTokenResponseDTO token;
    @NotNull
    UserResponseDTO user;
}
