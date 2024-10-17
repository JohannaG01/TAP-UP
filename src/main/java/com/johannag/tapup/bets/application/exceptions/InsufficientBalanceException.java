package com.johannag.tapup.bets.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ConflictException;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientBalanceException extends ConflictException {

    public InsufficientBalanceException(UUID userUuid, BigDecimal amount) {
        super(String.format("User with UUID %s has insufficient balance for amount %s.", userUuid, amount));
    }
}
