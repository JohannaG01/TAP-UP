package com.johannag.tapup.users.presentation.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class UserResponseDTO {
    UUID uuid;
    String email;
    String name;
    String lastName;
    BigDecimal balance;
}
