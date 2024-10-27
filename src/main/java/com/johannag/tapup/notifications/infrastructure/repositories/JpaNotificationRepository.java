package com.johannag.tapup.notifications.infrastructure.repositories;

import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long>,
        JpaSpecificationExecutor<NotificationEntity> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT n FROM NotificationEntity n WHERE n.uuid = :uuid")
    NotificationEntity findOneByUuidForUpdate(UUID uuid);
}