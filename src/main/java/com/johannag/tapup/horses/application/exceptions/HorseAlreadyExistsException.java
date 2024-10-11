package com.johannag.tapup.horses.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ConflictException;

import static com.johannag.tapup.horses.application.exceptions.HorseExceptionCode.HORSE_ALREADY_EXISTS;

public class HorseAlreadyExistsException extends ConflictException {

    public HorseAlreadyExistsException(String code) {
        super(String.format("Horse with code %S already exists", code), HORSE_ALREADY_EXISTS.toString());
    }
}
