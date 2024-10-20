package com.johannag.tapup.globals.infrastructure.db.entities;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditableEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @PrePersist
    protected void prePersist() {
        this.createdBy = SecurityContextUtils.userOnContextId();
        this.updatedBy = SecurityContextUtils.userOnContextId();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedBy = SecurityContextUtils.userOnContextId();
    }
}
