package com.johannag.tapup.auth.presentation.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(builderClassName = "Builder")
public class AuthTokenResponseDTO {
    String value;
    @Schema( example = "Bearer")
    String type;
    LocalDateTime issuedAt;
    LocalDateTime expiresAt;
}
