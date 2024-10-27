package com.johannag.tapup.notifications.domain.dtos;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UpdateNotificationReadStatusForEntityDTO {
    UUID notificationUuid;
    Boolean read;
}
