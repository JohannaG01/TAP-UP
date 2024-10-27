package com.johannag.tapup.users.presentation.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

import static com.johannag.tapup.globals.presentation.validations.constants.Messages.EMAIL_VALIDATION_MSG;
import static com.johannag.tapup.globals.presentation.validations.constants.Regex.EMAIL_RGX;

@Value
@Builder
public class LogInUserRequestDTO {

    @NotBlank
    @Pattern(regexp = EMAIL_RGX, message = EMAIL_VALIDATION_MSG)
    @Schema(example = "usuario@ejemplo.com")
    String email;

    @NotBlank
    @Schema(example = "PasswordSecure123$")
    String password;
}
