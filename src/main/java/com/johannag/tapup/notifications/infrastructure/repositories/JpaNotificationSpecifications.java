package com.johannag.tapup.notifications.infrastructure.repositories;

import com.johannag.tapup.globals.infrastructure.db.repositories.BaseSpecificationBuilder;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntityType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class JpaNotificationSpecifications {

    public static Specification<NotificationEntity> withUserUuid(UUID userUuid) {
        return (Root<NotificationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (userUuid == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("user").get("uuid"), userUuid);
        };
    }

    public static Specification<NotificationEntity> withTypes(List<NotificationEntityType> types) {
        return (Root<NotificationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (types == null || types.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("type").in(types);
        };
    }

    public static Specification<NotificationEntity> withSentFrom(LocalDateTime sentFrom) {
        return (Root<NotificationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (sentFrom == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("sentAt"), sentFrom);
        };
    }

    public static Specification<NotificationEntity> withSentTo(LocalDateTime sentTo) {
        return (Root<NotificationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (sentTo == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("sentAt"), sentTo);
        };
    }

    public static Specification<NotificationEntity> withIsRead(Boolean isRead) {
        return (Root<NotificationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (isRead == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("read"), isRead);
        };
    }

    public static class Builder extends BaseSpecificationBuilder<NotificationEntity> {

        public Builder withUserUuid(UUID userUuid) {
            if (userUuid != null) {
                spec = spec.and(JpaNotificationSpecifications.withUserUuid(userUuid));
            }
            return this;
        }

        public Builder withTypes(List<NotificationEntityType> types) {
            if (types != null && !types.isEmpty()) {
                spec = spec.and(JpaNotificationSpecifications.withTypes(types));
            }
            return this;
        }

        public Builder withSentFrom(LocalDateTime sentFrom) {
            if (sentFrom != null) {
                spec = spec.and(JpaNotificationSpecifications.withSentFrom(sentFrom));
            }
            return this;
        }

        public Builder withSentTo(LocalDateTime sentTo) {
            if (sentTo != null) {
                spec = spec.and(JpaNotificationSpecifications.withSentTo(sentTo));
            }
            return this;
        }

        public Builder withIsRead(Boolean isRead) {
            if (isRead != null) {
                spec = spec.and(JpaNotificationSpecifications.withIsRead(isRead));
            }
            return this;
        }

    }
}
