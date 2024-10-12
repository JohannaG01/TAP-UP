package com.johannag.tapup.users.presentation.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UserResponseDTO {
    UUID uuid;
    @Schema( example = "usuario@ejemplo.com")
    String email;
    @Schema( example = "Juan")
    String name;
    @Schema( example = "Perez")
    String lastName;
}
