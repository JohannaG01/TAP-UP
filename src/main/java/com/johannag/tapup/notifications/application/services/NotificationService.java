package com.johannag.tapup.notifications.application.services;

import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.dtos.FindNotificationsDTO;
import com.johannag.tapup.notifications.application.dtos.SendNotificationsInternalProcessDTO;
import com.johannag.tapup.notifications.application.dtos.UpdateNotificationReadStatusDTO;
import com.johannag.tapup.notifications.application.exceptions.NotificationNotFoundException;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    /**
     * Creates notifications based on the provided list of notification data transfer objects (DTOs).
     *
     * @param dtos a list of {@link CreateNotificationDTO} containing the details for the notifications to be created
     * @return a {@link CompletableFuture} containing a list of {@link NotificationModel} representing the created
     * notifications
     * @throws UserNotFoundException if any of the users specified in the notifications do not exist
     */
    List<NotificationModel> create(List<CreateNotificationDTO> dtos) throws UserNotFoundException;

    /**
     * Retrieves a paginated list of {@link NotificationModel} for a specified user.
     *
     * <p>This method takes a {@link FindNotificationsDTO} containing the search criteria
     * for notifications and returns a {@link Page} of {@link NotificationModel} objects
     * that match the criteria.</p>
     *
     * @param dto the {@link FindNotificationsDTO} containing the criteria for finding notifications,
     *            must not be null.
     * @return a {@link Page} of {@link NotificationModel} that match the search criteria for the user.
     * @throws UserNotFoundException if user does not exist.
     */
    Page<NotificationModel> findByUser(FindNotificationsDTO dto) throws UserNotFoundException;

    /**
     * Updates read status of a {@link NotificationModel} based on the provided {@link UpdateNotificationReadStatusDTO}.
     *
     * @param dto the {@link UpdateNotificationReadStatusDTO} containing the update details
     * @return the updated {@link NotificationModel}
     * @throws UserNotFoundException         if the user associated with the notification does not exist
     * @throws NotificationNotFoundException if the notification to update does not exist
     */
    NotificationModel updateReadStatusByUser(UpdateNotificationReadStatusDTO dto) throws UserNotFoundException,
            NotificationNotFoundException;

    /**
     * Sends notifications for internal processing.
     * <p>
     * This method takes a {@link SendNotificationsInternalProcessDTO} object
     * containing the necessary data to initiate the internal notification
     * processing workflow.
     *
     * @param dto the data transfer object containing the information required for
     *            sending notifications for internal processing
     */
    void sendForInternalProcess(SendNotificationsInternalProcessDTO dto);
}
