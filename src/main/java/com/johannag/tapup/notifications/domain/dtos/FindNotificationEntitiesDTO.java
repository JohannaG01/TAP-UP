package com.johannag.tapup.notifications.domain.dtos;

import com.johannag.tapup.notifications.domain.models.NotificationModelType;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class FindNotificationEntitiesDTO {
    int size;
    int page;
    UUID userUuid;
    Set<NotificationModelType> types;
    @Nullable
    LocalDateTime sentFrom;
    @Nullable
    LocalDateTime sentTo;
    @Nullable
    Boolean isRead;
}
