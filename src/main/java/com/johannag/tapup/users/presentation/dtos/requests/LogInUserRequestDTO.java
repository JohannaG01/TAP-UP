package com.johannag.tapup.users.presentation.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

import static com.johannag.tapup.globals.presentation.constants.Regex.EMAIL_RGX;
import static com.johannag.tapup.globals.presentation.constants.ValidationMessages.EMAIL_VALIDATION_MSG;

@Value
@Builder
public class LogInUserRequestDTO {
    @NotBlank
    @Pattern(regexp = EMAIL_RGX, message = EMAIL_VALIDATION_MSG)
    String email;
    @NotBlank
    String password;
}
