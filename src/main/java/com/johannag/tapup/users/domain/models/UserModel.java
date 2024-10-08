package com.johannag.tapup.users.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@ToString(exclude = "hashedPassword")
public class UserModel {
    private UUID uuid;
    private String email;
    private String name;
    private String lastName;
    private Boolean isAdmin;
    private BigDecimal balance;
    private String hashedPassword;
}
