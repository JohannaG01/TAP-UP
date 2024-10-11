package com.johannag.tapup.globals.presentation.advices;

import com.johannag.tapup.globals.presentation.errors.ValidationErrorResponse;
import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.globals.utils.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Order(1)
@RestControllerAdvice()
public class ValidationExceptionHandler {

    private static final Logger logger = Logger.getLogger(ValidationExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        List<ValidationErrorResponse.FieldError> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(this::mapToFieldError)
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .timestamp(DateTimeUtils.nowAsOffsetDateTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .fieldErrors(fieldErrors)
                .path(request.getServletPath())
                .build();

        logger.error("Validation errors occurred: {}", fieldErrors, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        List<ValidationErrorResponse.FieldError> fieldErrors = buildTypeMismatchFieldErrors(ex);

        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .timestamp(DateTimeUtils.nowAsOffsetDateTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getServletPath())
                .fieldErrors(fieldErrors)
                .build();

        logger.error("Validation errors occurred: {}", fieldErrors, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private ValidationErrorResponse.FieldError mapToFieldError(FieldError springFieldError) {
        return ValidationErrorResponse.FieldError.builder()
                .field(springFieldError.getField())
                .message(springFieldError.getDefaultMessage())
                .build();
    }

    private List<ValidationErrorResponse.FieldError> buildTypeMismatchFieldErrors(MethodArgumentTypeMismatchException ex) {
        return List.of(ValidationErrorResponse.FieldError.builder()
                .field(ex.getPropertyName())
                .message(buildTypeMismatchErrorMessage(ex))
                .build());
    }

    private String buildTypeMismatchErrorMessage(MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();
        return (requiredType != null && requiredType.isEnum()) ? buildEnumErrorMessage(ex.getValue(), requiredType) :
                ex.getMessage();
    }

    private String buildEnumErrorMessage(Object value, Class<?> requiredType) {
        String allowedValues = Arrays.toString(requiredType.getEnumConstants());
        return String.format(
                "'%s' is not a valid value for type '%s'. Must be one of: %s",
                value, requiredType.getSimpleName(), allowedValues);
    }
}
