package com.johannag.tapup.auth.presentation.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(builderClassName = "Builder")
public class AuthTokenResponseDTO {
    @NotNull
    String value;
    @Schema(example = "Bearer")
    @NotNull
    String type;
    @NotNull
    LocalDateTime issuedAt;
    @NotNull
    LocalDateTime expiresAt;
}
