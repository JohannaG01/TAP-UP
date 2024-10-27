package com.johannag.tapup.globals.presentation.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class ErrorResponse {
    @NotNull
    private OffsetDateTime timestamp;
    @NotNull
    private Integer status;
    @NotNull
    private String error;
    @NotNull
    private String message;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    @NotNull
    private String path;
}
