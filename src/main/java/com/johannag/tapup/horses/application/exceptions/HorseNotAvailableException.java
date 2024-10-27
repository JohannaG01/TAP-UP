package com.johannag.tapup.horses.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ConflictException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.horses.application.exceptions.HorseExceptionCode.HORSE_NOT_AVAILABLE;

public class HorseNotAvailableException extends ConflictException {

    public HorseNotAvailableException(List<UUID> horsesUuids, long days, LocalDateTime raceStartTime) {
        super(String.format("The following horses are not available: %s. They are either scheduled for a race within " +
                                "the next %d days or have participated in a race within the last %d days from the " +
                                "date %s.",
                        horsesUuids, days, days, raceStartTime),
                HORSE_NOT_AVAILABLE.toString());
    }
}
