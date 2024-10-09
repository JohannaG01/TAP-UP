package com.johannag.tapup.globals.presentation.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private OffsetDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    private String path;
}
