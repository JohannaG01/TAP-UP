package com.johannag.tapup.horses.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.NotFoundException;

import java.util.UUID;

import static com.johannag.tapup.horses.application.exceptions.HorseExceptionCode.HORSE_NOT_FOUND;

public class HorseNotFoundException extends NotFoundException {

    public HorseNotFoundException(UUID uuid) {
        super(String.format("Horse with uuid %s not found", uuid), HORSE_NOT_FOUND.toString());
    }
}
