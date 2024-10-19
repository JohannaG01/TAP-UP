package com.johannag.tapup.bets.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UnexpectedPaymentException extends ApiException {

    public UnexpectedPaymentException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
