package com.johannag.tapup.horses.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.UnprocessableEntityException;

public class CannotTemporallyInactivateHorseException extends UnprocessableEntityException {

    public CannotTemporallyInactivateHorseException(String message, String code) {
        super(message, code);
    }
}
