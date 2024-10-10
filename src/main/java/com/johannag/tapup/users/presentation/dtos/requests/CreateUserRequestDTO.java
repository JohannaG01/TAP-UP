package com.johannag.tapup.users.presentation.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import static com.johannag.tapup.globals.presentation.constants.Regex.EMAIL_RGX;
import static com.johannag.tapup.globals.presentation.constants.Regex.PASSWORD_RGX;
import static com.johannag.tapup.globals.presentation.constants.ValidationMessages.EMAIL_VALIDATION_MSG;
import static com.johannag.tapup.globals.presentation.constants.ValidationMessages.PASSWORD_VALIDATION_MSG;

@Value
public class CreateUserRequestDTO {

    @NotBlank
    @Pattern(regexp = EMAIL_RGX, message = EMAIL_VALIDATION_MSG)
    String email;
    @NotBlank
    String name;
    @NotBlank
    String lastName;
    @NotBlank
    @Pattern(regexp = PASSWORD_RGX, message = PASSWORD_VALIDATION_MSG)
    String password;
}
