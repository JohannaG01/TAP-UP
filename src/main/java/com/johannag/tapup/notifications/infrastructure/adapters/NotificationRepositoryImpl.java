package com.johannag.tapup.notifications.infrastructure.adapters;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.dtos.FindNotificationEntitiesDTO;
import com.johannag.tapup.notifications.domain.dtos.UpdateNotificationReadStatusForEntityDTO;
import com.johannag.tapup.notifications.domain.mappers.NotificationDomainMapper;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import com.johannag.tapup.notifications.infrastructure.repositories.JpaNotificationRepository;
import com.johannag.tapup.notifications.infrastructure.repositories.JpaNotificationSpecifications;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import com.johannag.tapup.users.infrastructure.db.repositories.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private static final Logger logger = Logger.getLogger(NotificationRepositoryImpl.class);
    private final JpaNotificationRepository jpaNotificationRepository;
    private final JpaUserRepository jpaUserRepository;
    private final NotificationDomainMapper notificationDomainMapper;

    @Override
    public List<NotificationModel> create(List<CreateNotificationEntityDTO> dtos) {
        logger.info("Creating notifications in DB");

        List<UUID> userUuids = dtos.stream()
                .map(CreateNotificationEntityDTO::getUserUuid)
                .toList();

        List<UserEntity> users = jpaUserRepository.findAllByUuidIn(userUuids);
        List<NotificationEntity> notificationEntities = notificationDomainMapper.toEntity(dtos, users);

        jpaNotificationRepository.saveAllAndFlush(notificationEntities);
        return notificationDomainMapper.toModel(notificationEntities);
    }

    @Override
    public Page<NotificationModel> findAll(FindNotificationEntitiesDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("sentAt").descending());

        Specification<NotificationEntity> spec = new JpaNotificationSpecifications.Builder()
                .withUserUuid(dto.getUserUuid())
                .withTypes(notificationDomainMapper.toEntity(dto.getTypes()))
                .withSentFrom(dto.getSentFrom())
                .withSentTo(dto.getSentTo())
                .withIsRead(dto.getIsRead())
                .build();

        return jpaNotificationRepository
                .findAll(spec, pageable)
                .map(notificationDomainMapper::toModel);
    }

    @Override
    @Transactional
    public NotificationModel updateReadStatus(UpdateNotificationReadStatusForEntityDTO dto) {
        logger.info("Updating notification read status in DB");
        NotificationEntity notification = jpaNotificationRepository.findOneByUuidForUpdate(dto.getNotificationUuid());
        notification.setRead(dto.getRead());

        jpaNotificationRepository.saveAndFlush(notification);

        return notificationDomainMapper.toModel(notification);
    }

}
