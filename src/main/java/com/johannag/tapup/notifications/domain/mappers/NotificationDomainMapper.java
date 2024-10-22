package com.johannag.tapup.notifications.domain.mappers;

import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.domain.models.NotificationModelType;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntityType;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;

import java.util.Collection;
import java.util.List;

public interface NotificationDomainMapper {

    /**
     * Converts a list of {@link CreateNotificationEntityDTO} objects to a list of
     * {@link NotificationEntity} objects.
     *
     * @param dtos  The list of {@link CreateNotificationEntityDTO} objects to be converted.
     * @param users The list of {@link UserEntity} objects to associate with the notifications.
     * @return A list of {@link NotificationEntity} objects that correspond to the provided DTOs.
     */
    List<NotificationEntity> toEntity(List<CreateNotificationEntityDTO> dtos, List<UserEntity> users);

    /**
     * Converts a Collection of {@link NotificationModelType} to a list of {@link NotificationEntityType}.
     *
     * <p>This method maps each {@link NotificationModelType} to its corresponding
     * {@link NotificationEntityType}, enabling the persistence or processing of notification types
     * within the system.</p>
     *
     * @param types the Collection of {@link NotificationModelType} to be converted,
     *              must not be null or empty.
     * @return a list of {@link NotificationEntityType} containing the converted notification types.
     */
    List<NotificationEntityType> toEntity(Collection<NotificationModelType> types);

    /**
     * Converts a {@link NotificationModelType} to a {@link NotificationEntityType}.
     *
     * <p>This method maps the provided {@link NotificationModelType} to its corresponding
     * {@link NotificationEntityType}, allowing for the conversion of notification type data
     * from the model representation to the entity representation, which is typically used for
     * database interactions.</p>
     *
     * @param type the {@link NotificationModelType} to be converted,
     *             must not be null.
     * @return the corresponding {@link NotificationEntityType} containing the converted notification type data.
     */
    NotificationEntityType toEntity(NotificationModelType type);

    /**
     * Converts a {@link NotificationModelType} to a {@link NotificationEntityType}.
     *
     * <p>This method maps the provided {@link NotificationModelType} to its corresponding
     * {@link NotificationEntityType}, facilitating the conversion necessary for
     * persisting or processing notification types within the system.</p>
     *
     * @param entities the {@link NotificationModelType} to be converted,
     *                 must not be null.
     * @return the corresponding {@link NotificationEntityType} containing the converted notification type.
     */
    List<NotificationModel> toModel(List<NotificationEntity> entities);

    /**
     * Converts a {@link NotificationEntity} to a {@link NotificationModel}.
     *
     * <p>This method maps the provided {@link NotificationEntity} to its corresponding
     * {@link NotificationModel}, enabling the transformation of data from the entity
     * representation to the model representation, which is typically used in application logic.</p>
     *
     * @param entity the {@link NotificationEntity} to be converted,
     *               must not be null.
     * @return the corresponding {@link NotificationModel} containing the converted notification data.
     */
    NotificationModel toModel(NotificationEntity entity);
}
