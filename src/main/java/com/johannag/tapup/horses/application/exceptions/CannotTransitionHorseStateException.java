package com.johannag.tapup.horses.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.UnprocessableEntityException;

public class CannotTransitionHorseStateException extends UnprocessableEntityException {

    public CannotTransitionHorseStateException(String message, String code) {
        super(message, code);
    }
}
