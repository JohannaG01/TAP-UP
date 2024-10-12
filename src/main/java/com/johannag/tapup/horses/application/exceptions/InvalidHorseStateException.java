package com.johannag.tapup.horses.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.BadRequestException;
import com.johannag.tapup.horses.domain.models.HorseModelState;

public class InvalidHorseStateException extends BadRequestException {

    public InvalidHorseStateException(HorseModelState state) {
        super(String.format("Current operation does not support horse state %s", state));
    }
}
