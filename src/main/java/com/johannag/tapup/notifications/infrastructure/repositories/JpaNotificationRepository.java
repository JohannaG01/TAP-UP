package com.johannag.tapup.notifications.infrastructure.repositories;

import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {
}