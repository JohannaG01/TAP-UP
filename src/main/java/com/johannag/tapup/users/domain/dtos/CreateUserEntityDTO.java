package com.johannag.tapup.users.domain.dtos;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
@ToString(exclude = "hashedPassword")
public class CreateUserEntityDTO {
    UUID uuid;
    String email;
    String name;
    String lastName;
    Boolean isAdmin;
    BigDecimal balance;
    String hashedPassword;
}
