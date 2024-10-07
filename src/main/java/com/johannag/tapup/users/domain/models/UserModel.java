package com.johannag.tapup.users.domain.models;

import lombok.Builder;
import lombok.Data;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Model for {@link UserEntity}
 */

@Data
@Builder
public class UserModel {
    private UUID uuid;
    private String email;
    private String name;
    private String lastName;
    private Boolean isAdmin;
    private BigDecimal balance;
    private String hashedPassword;
}
