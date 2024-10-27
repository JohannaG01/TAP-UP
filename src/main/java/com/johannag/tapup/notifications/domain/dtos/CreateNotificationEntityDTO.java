package com.johannag.tapup.notifications.domain.dtos;

import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntityType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class CreateNotificationEntityDTO {
    UUID uuid;
    UUID userUuid;
    String message;
    NotificationEntityType type;
    LocalDateTime sentAt;
    Boolean read;
}
