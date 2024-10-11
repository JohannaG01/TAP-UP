package com.johannag.tapup.auth.presentation.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(builderClassName = "Builder")
public class AuthTokenResponseDTO {
    String value;
    String type;
    LocalDateTime issuedAt;
    LocalDateTime expiresAt;
}
