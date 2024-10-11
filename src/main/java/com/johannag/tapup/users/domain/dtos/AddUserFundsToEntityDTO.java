package com.johannag.tapup.users.domain.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class AddUserFundsToEntityDTO {
    UUID userUuid;
    BigDecimal amount;
}
