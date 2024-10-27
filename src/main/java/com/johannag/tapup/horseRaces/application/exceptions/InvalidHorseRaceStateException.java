package com.johannag.tapup.horseRaces.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ConflictException;

import static com.johannag.tapup.horseRaces.application.exceptions.HorseRaceExceptionCode.INVALID_STATE_FOR_OPERATION;

public class InvalidHorseRaceStateException extends ConflictException {

    public InvalidHorseRaceStateException(String message) {
        super(message, INVALID_STATE_FOR_OPERATION.toString());
    }
}
