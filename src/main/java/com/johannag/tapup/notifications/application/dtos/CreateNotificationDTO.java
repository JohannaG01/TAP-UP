package com.johannag.tapup.notifications.application.dtos;

import com.johannag.tapup.notifications.domain.models.NotificationModelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
@AllArgsConstructor
public class CreateNotificationDTO {
    UUID userUuid;
    String message;
    NotificationModelType type;
}
