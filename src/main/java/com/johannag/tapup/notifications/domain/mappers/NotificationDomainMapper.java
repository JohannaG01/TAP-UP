package com.johannag.tapup.notifications.domain.mappers;

import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;

import java.util.List;

public interface NotificationDomainMapper {

    /**
     * Converts a list of {@link CreateNotificationEntityDTO} objects to a list of
     * {@link NotificationEntity} objects.
     *
     * @param dtos    The list of {@link CreateNotificationEntityDTO} objects to be converted.
     * @param users   The list of {@link UserEntity} objects to associate with the notifications.
     * @return       A list of {@link NotificationEntity} objects that correspond to the provided DTOs.
     */
    List<NotificationEntity> toEntity(List<CreateNotificationEntityDTO> dtos, List<UserEntity> users);

    /**
     * Converts a list of {@link NotificationEntity} objects into a list of {@link NotificationModel} objects.
     *
     * @param entities a list of {@link NotificationEntity} objects to be converted
     * @return a list of {@link NotificationModel} objects corresponding to the provided entities
     */
    List<NotificationModel> toModel(List<NotificationEntity> entities);
}
