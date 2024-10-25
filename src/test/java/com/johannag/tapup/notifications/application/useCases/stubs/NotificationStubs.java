package com.johannag.tapup.notifications.application.useCases.stubs;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.notifications.application.dtos.FindNotificationsDTO;
import com.johannag.tapup.notifications.application.dtos.UpdateNotificationReadStatusDTO;
import com.johannag.tapup.notifications.domain.dtos.FindNotificationEntitiesDTO;
import com.johannag.tapup.notifications.domain.dtos.UpdateNotificationReadStatusForEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.domain.models.NotificationModelType;
import com.johannag.tapup.users.application.useCases.stubs.UserStubs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public class NotificationStubs {

    public static NotificationModel notificationModel() {
        return NotificationModel.builder()
                .uuid(UUID.randomUUID())
                .user(UserStubs.userModel())
                .type(NotificationModelType.REMINDER)
                .message("Notification")
                .sentAt(DateTimeUtils.nowAsLocalDateTime())
                .read(false)
                .build();
    }

    public static Page<NotificationModel> notificationModelPage() {
        Pageable pageable = Pageable.ofSize(10);
        return new PageImpl<>(List.of(notificationModel()), pageable, 10);
    }

    public static FindNotificationsDTO findNotificationsDTO() {
        return FindNotificationsDTO.builder()
                .page(0)
                .size(10)
                .build();
    }

    public static FindNotificationEntitiesDTO findNotificationEntitiesDTO() {
        return FindNotificationEntitiesDTO.builder()
                .page(0)
                .size(10)
                .build();
    }

    public static UpdateNotificationReadStatusDTO updateNotificationReadStatusDTO() {
        return UpdateNotificationReadStatusDTO.builder()
                .userUuid(UUID.randomUUID())
                .notificationUuid(UUID.randomUUID())
                .read(true)
                .build();
    }

    public static UpdateNotificationReadStatusForEntityDTO updateNotificationReadStatusForEntityDTO() {
        return UpdateNotificationReadStatusForEntityDTO.builder()
                .notificationUuid(UUID.randomUUID())
                .read(true)
                .build();
    }
}
