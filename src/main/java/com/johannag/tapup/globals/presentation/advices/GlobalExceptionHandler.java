package com.johannag.tapup.globals.presentation.advices;

import com.johannag.tapup.globals.application.exceptions.ApiException;
import com.johannag.tapup.globals.presentation.errors.ErrorResponse;
import com.johannag.tapup.utils.DateTimeUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException ex, HandlerMethod handlerMethod, HttpServletRequest request) {
        return handleErrorResponse(ex, handlerMethod, request, ex.getHttpStatus(), ex.getCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex, HandlerMethod handlerMethod, HttpServletRequest request) {
        return handleErrorResponse(ex, handlerMethod, request, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    private ResponseEntity<ErrorResponse> handleErrorResponse(
            Exception ex,
            HandlerMethod handlerMethod,
            HttpServletRequest request,
            HttpStatus status,
            @Nullable String code) {

        logError(ex, handlerMethod);
        return new ResponseEntity<>(buildErrorResponse(ex, request, status, code), status);
    }

    private void logError(Exception ex, HandlerMethod handlerMethod) {
        String exClassName = ex.getClass().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        String beanName = handlerMethod.getBeanType().getSimpleName();

        logger.error("{} occurred in method [{}] of class [{}]. Error: {}", exClassName, methodName, beanName,
                ex.getMessage(), ex);
    }

    private ErrorResponse buildErrorResponse(
            Exception ex, HttpServletRequest request, HttpStatus status, @Nullable String code) {

        return ErrorResponse.builder()
                .timestamp(DateTimeUtils.nowAsOffsetDateTime())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getServletPath())
                .code(code)
                .build();
    }

}
