package com.johannag.tapup.notifications.application.mappers;

import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.dtos.FindNotificationsDTO;
import com.johannag.tapup.notifications.application.dtos.UpdateNotificationReadStatusDTO;
import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.dtos.FindNotificationEntitiesDTO;
import com.johannag.tapup.notifications.domain.dtos.UpdateNotificationReadStatusForEntityDTO;
import com.johannag.tapup.notifications.presentation.dtos.queries.FindNotificationsQuery;
import com.johannag.tapup.notifications.presentation.dtos.requests.UpdateNotificationReadStatusRequestDTO;

import java.util.List;
import java.util.UUID;

public interface NotificationApplicationMapper {

    /**
     * Converts a list of {@link CreateNotificationDTO} to a list of
     * {@link CreateNotificationEntityDTO}.
     *
     * <p>This method maps each {@link CreateNotificationDTO} to a corresponding
     * {@link CreateNotificationEntityDTO}, allowing for further processing or
     * storage of the notification data.</p>
     *
     * @param dtos the list of {@link CreateNotificationDTO} to be converted,
     *             must not be null or empty.
     * @return a list of {@link CreateNotificationEntityDTO} containing the converted
     * notification data.
     */
    List<CreateNotificationEntityDTO> toCreateDTO(List<CreateNotificationDTO> dtos);

    /**
     * Converts a {@link FindNotificationsQuery} into a {@link FindNotificationsDTO}.
     *
     * <p>This method takes a query object representing the search criteria for notifications
     * and the user ID associated with the query, and maps it to a data transfer object
     * that can be used for further processing or API responses.</p>
     *
     * @param query    the {@link FindNotificationsQuery} containing the criteria for finding notifications,
     *                 must not be null.
     * @param userUuid the UUID of the user for whom the notifications are being queried,
     *                 must not be null.
     * @return a {@link FindNotificationsDTO} representing the mapped notification criteria.
     * @throws IllegalArgumentException if either {@code query} or {@code userUuid} is null.
     */
    FindNotificationsDTO toFindDTO(FindNotificationsQuery query, UUID userUuid);

    /**
     * Converts a {@link FindNotificationsDTO} into a {@link FindNotificationEntitiesDTO}.
     *
     * <p>This method takes an input DTO containing notification search criteria and maps it
     * to a {@link FindNotificationEntitiesDTO} that can be used for further processing
     * or storage of notification data.</p>
     *
     * @param dto the {@link FindNotificationsDTO} containing the criteria for finding notifications,
     *            must not be null.
     * @return a {@link FindNotificationEntitiesDTO} representing the mapped notification search criteria.
     */
    FindNotificationEntitiesDTO toFindEntitiesDTO(FindNotificationsDTO dto);

    /**
     * Converts the provided {@link UpdateNotificationReadStatusRequestDTO} into an
     * {@link UpdateNotificationReadStatusDTO} for updating the read status of a notification.
     *
     * @param userUuid         the UUID of the user associated with the notification
     * @param notificationUuid the UUID of the notification to update
     * @param dto              the {@link UpdateNotificationReadStatusRequestDTO} containing the new read status
     * @return an {@link UpdateNotificationReadStatusDTO} containing the user UUID,
     * notification UUID, and the new read status
     */
    UpdateNotificationReadStatusDTO toUpdateDTO(UUID userUuid, UUID notificationUuid,
                                                UpdateNotificationReadStatusRequestDTO dto);

    /**
     * Converts the provided {@link UpdateNotificationReadStatusDTO} into an
     * {@link UpdateNotificationReadStatusForEntityDTO} for updating the read status
     * of a notification entity.
     *
     * @param dto the {@link UpdateNotificationReadStatusDTO} containing the user and
     *            notification information along with the new read status
     * @return an {@link UpdateNotificationReadStatusForEntityDTO} containing the necessary
     * information to update the notification entity
     */
    UpdateNotificationReadStatusForEntityDTO toUpdateEntityDTO(UpdateNotificationReadStatusDTO dto);
}
