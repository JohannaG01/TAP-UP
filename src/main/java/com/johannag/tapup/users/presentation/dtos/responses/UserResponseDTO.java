package com.johannag.tapup.users.presentation.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UserResponseDTO {

    @NotNull
    UUID uuid;
    @Schema(example = "usuario@ejemplo.com")

    @NotNull
    String email;

    @Schema(example = "Juan")
    @NotNull
    String name;

    @Schema(example = "Perez")
    @NotNull
    String lastName;
}
