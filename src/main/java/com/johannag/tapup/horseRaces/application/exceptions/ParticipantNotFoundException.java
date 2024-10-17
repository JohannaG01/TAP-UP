package com.johannag.tapup.horseRaces.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ConflictException;

import java.util.UUID;

import static com.johannag.tapup.horseRaces.application.exceptions.HorseRaceExceptionCode.PARTICIPANT_NOT_FOUND;

public class ParticipantNotFoundException extends ConflictException {

    public ParticipantNotFoundException(UUID uuid) {
        super(String.format("Participant with uuid %s not found for any horse race", uuid),
                PARTICIPANT_NOT_FOUND.toString());
    }
}