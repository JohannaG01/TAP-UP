package com.johannag.tapup.notifications.application.dtos;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UpdateNotificationReadStatusDTO {
    UUID userUuid;
    UUID notificationUuid;
    Boolean read;
}
