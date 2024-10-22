package com.johannag.tapup.notifications.infrastructure.db.entities;

import com.johannag.tapup.globals.infrastructure.db.entities.AuditableEntity;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Entity
@Table(name = "notifications")
public class NotificationEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "message", nullable = false, updatable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private NotificationEntityType type;

    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @Column(name = "read", nullable = false)
    private Boolean read;
}
