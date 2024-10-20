package com.johannag.tapup.notifications.infrastructure.adapters;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.mappers.NotificationDomainMapper;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import com.johannag.tapup.notifications.infrastructure.repositories.JpaNotificationRepository;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final JpaNotificationRepository jpaNotificationRepository;
    private final JpaUserRepository jpaUserRepository;
    private final NotificationDomainMapper notificationDomainMapper;
    private static final Logger logger = Logger.getLogger(NotificationRepositoryImpl.class);

    @Override
    public List<NotificationModel> create(List<CreateNotificationEntityDTO> dtos) {
        logger.info("Creating notifications in DB");

        List<UUID> userUuids = dtos.stream()
                .map(CreateNotificationEntityDTO::getUserUuid)
                .toList();

        List<UserEntity> users = jpaUserRepository.findAllByUuidIn(userUuids);
        List<NotificationEntity> notificationEntities = notificationDomainMapper.toEntity(dtos, users);
        notificationEntities.forEach(notificationEntity -> {
            notificationEntity.setCreatedBy(SecurityContextUtils.userOnContextId());
            notificationEntity.setUpdatedBy(SecurityContextUtils.userOnContextId());
        });

        jpaNotificationRepository.saveAllAndFlush(notificationEntities);

        return notificationDomainMapper.toModel(notificationEntities);
    }

}
