package com.johannag.tapup.horseRaces.exceptions;

import com.johannag.tapup.globals.application.exceptions.NotFoundException;

import java.util.UUID;

import static com.johannag.tapup.horseRaces.exceptions.HorseRaceExceptionCode.HORSE_RACE_NOT_FOUND;

public class HorseRaceNotFoundException extends NotFoundException {

    public HorseRaceNotFoundException(UUID horseRaceUuid) {
        super(String.format("Horse Race with uuid %s not found", horseRaceUuid), HORSE_RACE_NOT_FOUND.toString());
    }
}
