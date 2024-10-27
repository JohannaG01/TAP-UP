package com.johannag.tapup.notifications.infrastructure.adapters;

import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.dtos.FindNotificationEntitiesDTO;
import com.johannag.tapup.notifications.domain.dtos.UpdateNotificationReadStatusForEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationRepository {

    /**
     * Creates a list of {@link NotificationModel} from a list of
     * {@link CreateNotificationEntityDTO}.
     *
     * <p>This method processes the provided list of {@link CreateNotificationEntityDTO}
     * to create corresponding {@link NotificationModel} instances. The created notifications
     * can be used for further operations such as saving to a database or sending to users.</p>
     *
     * @param dtos the list of {@link CreateNotificationEntityDTO} used to create
     *             {@link NotificationModel} instances, must not be null or empty.
     * @return a list of {@link NotificationModel} created from the input DTOs.
     */
    List<NotificationModel> create(List<CreateNotificationEntityDTO> dtos);

    /**
     * Retrieves a paginated list of {@link NotificationModel} based on the specified criteria.
     *
     * <p>This method takes a {@link FindNotificationEntitiesDTO} containing the search criteria
     * for retrieving notifications and returns a {@link Page} of {@link NotificationModel} objects
     * that match the criteria.</p>
     *
     * @param dto the {@link FindNotificationEntitiesDTO} containing the criteria for finding notifications,
     *            must not be null.
     * @return a {@link Page} of {@link NotificationModel} that match the search criteria.
     * @throws IllegalArgumentException if {@code dto} is null.
     */
    Page<NotificationModel> findAll(FindNotificationEntitiesDTO dto);

    /**
     * Updates the read status of a notification based on the provided
     * {@link UpdateNotificationReadStatusForEntityDTO}.
     *
     * @param dto the {@link UpdateNotificationReadStatusForEntityDTO} containing the
     *            notification ID and the new read status
     * @return the updated {@link NotificationModel} reflecting the new read status
     */
    NotificationModel updateReadStatus(UpdateNotificationReadStatusForEntityDTO dto);
}
