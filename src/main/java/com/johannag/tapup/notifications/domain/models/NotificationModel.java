package com.johannag.tapup.notifications.domain.models;

import com.johannag.tapup.users.domain.models.UserModel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder(builderClassName = "Builder")
public class NotificationModel {
    private final UUID uuid;
    private final UserModel user;
    private final NotificationModelType type;
    private final LocalDateTime sentAt;
    private final Boolean read;
}