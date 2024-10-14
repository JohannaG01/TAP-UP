package com.johannag.tapup.users.presentation.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import static com.johannag.tapup.globals.presentation.validations.constants.Messages.EMAIL_VALIDATION_MSG;
import static com.johannag.tapup.globals.presentation.validations.constants.Messages.PASSWORD_VALIDATION_MSG;
import static com.johannag.tapup.globals.presentation.validations.constants.Regex.EMAIL_RGX;
import static com.johannag.tapup.globals.presentation.validations.constants.Regex.PASSWORD_RGX;

@Value
public class CreateUserRequestDTO {

    @NotBlank
    @Pattern(regexp = EMAIL_RGX, message = EMAIL_VALIDATION_MSG)
    @Schema(example = "usuario@ejemplo.com")
    String email;

    @NotBlank
    @Schema(example = "Juan")
    String name;

    @NotBlank
    @Schema(example = "Perez")
    String lastName;

    @NotBlank
    @Pattern(regexp = PASSWORD_RGX, message = PASSWORD_VALIDATION_MSG)
    @Schema(example = "PasswordSecure123$")
    String password;
}
