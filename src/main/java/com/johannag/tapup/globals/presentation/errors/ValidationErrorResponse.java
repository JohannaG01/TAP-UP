package com.johannag.tapup.globals.presentation.errors;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ValidationErrorResponse extends ErrorResponse {
    List<FieldError> fieldErrors;

    @Value
    @Builder
    public static class FieldError {
        String field;
        String message;
    }
}
