package com.johannag.tapup.globals.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Getter
public abstract class ApiException extends RuntimeException {

    @Nullable
    private final String code;
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = null;
    }

    public ApiException(String message, HttpStatus httpStatus, @Nullable String code) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
