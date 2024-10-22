package com.johannag.tapup.notifications.infrastructure.adapters;

import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;

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
}
