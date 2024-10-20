package com.johannag.tapup.notifications.application.mappers;

import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;

import java.util.List;

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
     *         notification data..
     */
    List<CreateNotificationEntityDTO> toCreateDTO(List<CreateNotificationDTO> dtos);
}
