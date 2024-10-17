package com.johannag.tapup.users.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
@AllArgsConstructor
public class SubtractUserFundsDTO {
    UUID userUuid;
    BigDecimal amount;
}
