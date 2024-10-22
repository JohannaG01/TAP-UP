package com.johannag.tapup.notifications.application.exceptions;

import com.johannag.tapup.globals.application.exceptions.NotFoundException;

import java.util.UUID;

import static com.johannag.tapup.notifications.application.exceptions.NotificationExceptionCode.NOTIFICATION_NOT_FOUND;

public class NotificationNotFoundException extends NotFoundException {

    public NotificationNotFoundException(UUID uuid) {
        super(String.format("Notification with uuid %s not found", uuid), NOTIFICATION_NOT_FOUND.toString());
    }
}
