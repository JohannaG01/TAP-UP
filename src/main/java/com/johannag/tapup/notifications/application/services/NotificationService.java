package com.johannag.tapup.notifications.application.services;

import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    /**
     * Creates notifications based on the provided list of notification data transfer objects (DTOs).
     *
     * @param dtos a list of {@link CreateNotificationDTO} containing the details for the notifications to be created
     * @return a {@link CompletableFuture} containing a list of {@link NotificationModel} representing the created notifications
     * @throws UserNotFoundException if any of the users specified in the notifications do not exist
     */
    CompletableFuture<List<NotificationModel>> createNotifications(List<CreateNotificationDTO> dtos) throws UserNotFoundException;
}
