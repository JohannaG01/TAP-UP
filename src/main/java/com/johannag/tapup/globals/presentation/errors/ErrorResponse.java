package com.johannag.tapup.globals.presentation.errors;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Value
@Builder
public class ErrorResponse {
    OffsetDateTime timestamp;
    Integer status;
    String error;
    String message;
    @Nullable
    String code;
    String path;
}
