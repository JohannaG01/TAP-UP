package com.johannag.tapup.horses.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.ApiException;
import com.johannag.tapup.globals.application.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.horses.application.exceptions.HorseExceptionCode.HORSE_NOT_FOUND;

public class HorseNotFoundException extends ApiException {

    public HorseNotFoundException(UUID uuid) {
        super(String.format("Horse with uuid %s not found", uuid), HttpStatus.NOT_FOUND, HORSE_NOT_FOUND.toString());
    }

    public HorseNotFoundException(List<UUID> uuids) {
        super(String.format("Horse with uuids %s not found", uuids), HttpStatus.CONFLICT, HORSE_NOT_FOUND.toString());
    }
}
