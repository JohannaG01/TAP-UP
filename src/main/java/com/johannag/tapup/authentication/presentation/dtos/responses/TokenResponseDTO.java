package com.johannag.tapup.authentication.presentation.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(builderClassName = "Builder")
public class TokenResponseDTO {
    String value;
    String type;
    LocalDateTime issuedAt;
    LocalDateTime expiresAt;
}
